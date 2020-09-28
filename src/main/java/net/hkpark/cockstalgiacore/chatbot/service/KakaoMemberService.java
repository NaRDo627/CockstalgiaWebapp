package net.hkpark.cockstalgiacore.chatbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgiacore.chatbot.exception.MemberAlreadyExistsException;
import net.hkpark.cockstalgiacore.chatbot.exception.MemberNotFoundException;
import net.hkpark.cockstalgiacore.core.constants.ErrorMessage;
import net.hkpark.cockstalgiacore.core.dto.ResultDto;
import net.hkpark.cockstalgiacore.core.entity.Admin;
import net.hkpark.cockstalgiacore.core.entity.Member;
import net.hkpark.cockstalgiacore.core.exception.InvalidValueException;
import net.hkpark.cockstalgiacore.core.repository.AdminRepository;
import net.hkpark.cockstalgiacore.core.repository.MemberRepository;
import net.hkpark.kakao.openbuilder.dto.ActionDto;
import net.hkpark.kakao.openbuilder.dto.SkillRequestDto;
import net.hkpark.kakao.openbuilder.dto.UserDto;
import net.hkpark.kakao.openbuilder.dto.UserPropertiesDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoMemberService {
    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;

    public ResultDto confirmPlusFriend(SkillRequestDto skillRequestDto) {
        if (isPlusFriend(skillRequestDto)) {
            return ResultDto.builder().data("콕스 부원 인증되었습니다.").build();
        }
        return ResultDto.builder().data("부원이 아닙니다. 누구냐 넌...").build();
    }

    @Transactional
    public ResultDto registerKakaoMember(SkillRequestDto skillRequestDto) {
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

        return ResultDto.builder().data("등록되었습니다.").build();
    }

    @Transactional
    public ResultDto registerAdmin(SkillRequestDto skillRequestDto) {
        UserDto userDto = skillRequestDto.getUserRequest().getUser();
        String kakaoBotUserId = userDto.getId();

        Member member = memberRepository.findByKakaoBotUserId(kakaoBotUserId).orElseThrow(MemberNotFoundException::new);
        if (Objects.nonNull(member.getAdmin())) {
            if (! member.getAdmin().isAuthorized()) {
                return ResultDto.builder().data("승인 대기중입니다.").build();
            }

            return ResultDto.builder().data("이미 신청되었습니다.").build();
        }
        Admin admin = member.registerAdmin();
        adminRepository.save(admin);

        return ResultDto.builder().data("신청되었습니다.").build();
    }

    private boolean isPlusFriend(SkillRequestDto skillRequestDto) {
        try {
            return skillRequestDto.getUserRequest().getUser().getProperties().isFriend();
        } catch (NullPointerException e) {
            return false;
        }
    }
}
