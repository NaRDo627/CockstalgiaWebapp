package net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.exception.MemberAlreadyExistsException;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.exception.MemberNotFoundException;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.util.SkillResponseUtil;
import net.hkpark.cockstalgia.core.constant.ErrorMessage;
import net.hkpark.cockstalgia.core.entity.Admin;
import net.hkpark.cockstalgia.core.entity.Member;
import net.hkpark.cockstalgia.core.exception.InvalidValueException;
import net.hkpark.cockstalgia.core.repository.AdminRepository;
import net.hkpark.cockstalgia.core.repository.MemberRepository;
import net.hkpark.kakao.openbuilder.dto.request.ActionDto;
import net.hkpark.kakao.openbuilder.dto.request.SkillRequestDto;
import net.hkpark.kakao.openbuilder.dto.request.UserDto;
import net.hkpark.kakao.openbuilder.dto.request.UserPropertiesDto;
import net.hkpark.kakao.openbuilder.dto.response.SkillResponseDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;

    public SkillResponseDto confirmPlusFriend(SkillRequestDto skillRequestDto) {
        if (isPlusFriend(skillRequestDto)) {
            return SkillResponseUtil.simpleTextResponseDto("콕스 부원 인증되었습니다.");
        }
        return SkillResponseUtil.simpleTextResponseDto("부원이 아닙니다. 누구냐 넌...");
    }

    @Transactional
    public SkillResponseDto registerKakaoMember(SkillRequestDto skillRequestDto) {
        UserDto userDto = skillRequestDto.getUserRequest().getUser();
        ActionDto actionDto = skillRequestDto.getAction();
        UserPropertiesDto userPropertiesDto = userDto.getProperties();
        String kakaoBotUserId = userDto.getId();
        String kakaoPlusFriendKey = userPropertiesDto.getPlusfriendUserKey();
        if (StringUtils.isEmpty(actionDto.getParams().get("person_name"))) {
            throw new InvalidValueException(ErrorMessage.REQUEST_BAD_REQUEST);
        }
        String memberName = actionDto.getParams().get("person_name").toString();

        Member newMember = Member.builder()
                .name(memberName)
                .kakaoBotUserId(kakaoBotUserId)
                .kakaoPlusFriendKey(kakaoPlusFriendKey)
                .build();

        try {
            memberRepository.save(newMember);
        } catch (DataIntegrityViolationException e) { // 이 경우 중복 데이터일 가능성이 높다.
            String msg = ErrorMessage.DB_INSERT_FAILURE_ALREADY_EXISTS;
            log.error(msg, "member", e);
            throw new MemberAlreadyExistsException(e);
        }

        return SkillResponseUtil.simpleTextResponseDto("등록되었습니다.");
    }

    @Transactional
    public SkillResponseDto registerAdmin(SkillRequestDto skillRequestDto) {
        UserDto userDto = skillRequestDto.getUserRequest().getUser();
        String kakaoBotUserId = userDto.getId();

        Member member = memberRepository.findByKakaoBotUserId(kakaoBotUserId).orElseThrow(MemberNotFoundException::new);
        if (Objects.nonNull(member.getAdmin())) {
            if (! member.getAdmin().isAuthorized()) {
                return SkillResponseUtil.simpleTextResponseDto("승인 대기중입니다.");
            }

            return SkillResponseUtil.simpleTextResponseDto("이미 신청되었습니다.");
        }
        Admin admin = member.registerAdmin();
        adminRepository.save(admin);

        return SkillResponseUtil.simpleTextResponseDto("신청되었습니다.");
    }

    private boolean isPlusFriend(SkillRequestDto skillRequestDto) {
        try {
            return skillRequestDto.getUserRequest().getUser().getProperties().isFriend();
        } catch (NullPointerException e) {
            return false;
        }
    }
}
