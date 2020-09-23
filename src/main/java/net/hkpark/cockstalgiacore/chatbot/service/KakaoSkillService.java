package net.hkpark.cockstalgiacore.chatbot.service;

import net.hkpark.cockstalgiacore.core.dto.ResultDto;
import net.hkpark.kakao.openbuilder.dto.SkillRequestDto;
import org.springframework.stereotype.Service;

@Service
public class KakaoSkillService {
    public ResultDto confirmPlusFriend(SkillRequestDto skillRequestDto) {
        if (isPlusFriend(skillRequestDto)) {
            return ResultDto.builder().data("콕스 부원 인증되었습니다.").build();
        }
        return ResultDto.builder().data("부원이 아닙니다. 누구냐 넌...").build();
    }

    private boolean isPlusFriend(SkillRequestDto skillRequestDto) {
        try {
            return skillRequestDto.getUserRequest().getUser().getProperties().isFriend();
        } catch (NullPointerException e) {
            return false;
        }
    }
}
