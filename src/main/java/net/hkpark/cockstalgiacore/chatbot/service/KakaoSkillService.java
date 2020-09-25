package net.hkpark.cockstalgiacore.chatbot.service;

import lombok.RequiredArgsConstructor;
import net.hkpark.cockstalgiacore.core.dto.ResultDto;
import net.hkpark.cockstalgiacore.core.entity.Member;
import net.hkpark.cockstalgiacore.core.repository.MemberRepository;
import net.hkpark.cockstalgiacore.core.service.MemberService;
import net.hkpark.kakao.openbuilder.dto.ActionDto;
import net.hkpark.kakao.openbuilder.dto.SkillRequestDto;
import net.hkpark.kakao.openbuilder.dto.UserDto;
import net.hkpark.kakao.openbuilder.dto.UserPropertiesDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KakaoSkillService {
    private final MemberRepository memberRepository;

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
        String memberName = actionDto.getParams().get("person_name").toString();

        Member newMember = Member.builder()
                .name(memberName)
                .kakaoBotUserId(kakaoBotUserId)
                .kakaoPlusFriendKey(kakaoPlusFriendKey)
                .build();

        memberRepository.save(newMember);
        return ResultDto.builder().data("OK").build();
    }

    private boolean isPlusFriend(SkillRequestDto skillRequestDto) {
        try {
            return skillRequestDto.getUserRequest().getUser().getProperties().isFriend();
        } catch (NullPointerException e) {
            return false;
        }
    }
}
