package net.hkpark.cockstalgia.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.core.entity.Member;
import net.hkpark.cockstalgia.core.repository.MemberRepository;
import net.hkpark.cockstalgia.core.util.JsonUtil;
import net.hkpark.cockstalgia.core.util.SecurityUtil;
import net.hkpark.cockstalgia.core.vo.MemberIdentityKeyBaseVo;
import net.hkpark.kakao.developers.dto.response.KakaoAccountDto;
import net.hkpark.kakao.developers.dto.response.KakaoUserInfoDto;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOauth2Service extends Oauth2Service {
    private final MemberRepository memberRepository;

    public String getUserKeyFromAttributes(Map<String, Object> userAttributes) {
        KakaoUserInfoDto userInfoDto = JsonUtil.convertMapToObjectClass(userAttributes, KakaoUserInfoDto.class);
        KakaoAccountDto kakaoAccountDto = userInfoDto.getKakaoAccount();
        Map<String, Object> properties = userInfoDto.getProperties();
        String realname = (String)properties.get("realname");
        String birthday = kakaoAccountDto.getBirthday();
        MemberIdentityKeyBaseVo memberIdentityKeyBaseVo =
                MemberIdentityKeyBaseVo.builder().realname(realname).birthday(birthday).build();
        return SecurityUtil.encodeUserKey(memberIdentityKeyBaseVo);
    }


}
