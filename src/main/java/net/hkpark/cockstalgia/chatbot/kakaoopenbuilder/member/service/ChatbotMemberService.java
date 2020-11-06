package net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.exception.MemberAlreadyExistsException;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.exception.MemberNotFoundException;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.util.SkillResponseUtil;
import net.hkpark.cockstalgia.core.constant.ErrorMessage;
import net.hkpark.cockstalgia.core.entity.Member;
import net.hkpark.cockstalgia.core.exception.InvalidValueException;
import net.hkpark.cockstalgia.core.repository.MemberRepository;
import net.hkpark.cockstalgia.core.service.MemberEntityService;
import net.hkpark.kakao.openbuilder.dto.request.ActionDto;
import net.hkpark.kakao.openbuilder.dto.request.SkillRequestDto;
import net.hkpark.kakao.openbuilder.dto.request.UserDto;
import net.hkpark.kakao.openbuilder.dto.request.UserPropertiesDto;
import net.hkpark.kakao.openbuilder.dto.response.SkillResponseDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatbotMemberService {
    private final MemberEntityService memberEntityService;

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
        // TODO 날짜 데이터 추가 입력 필요!!!

        Member newMember = Member.builder()
                .name(memberName)
                .kakaoBotUserId(kakaoBotUserId)
                .kakaoPlusFriendKey(kakaoPlusFriendKey)
                .build();

        memberEntityService.saveMember(newMember);

        return SkillResponseUtil.simpleTextResponseDto("등록되었습니다.");
    }

    private boolean isPlusFriend(SkillRequestDto skillRequestDto) {
        try {
            return skillRequestDto.getUserRequest().getUser().getProperties().isFriend();
        } catch (NullPointerException e) {
            return false;
        }
    }
}
