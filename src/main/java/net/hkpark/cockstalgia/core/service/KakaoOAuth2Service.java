package net.hkpark.cockstalgia.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.core.util.JsonUtil;
import net.hkpark.cockstalgia.core.util.OAuth2Util;
import net.hkpark.cockstalgia.core.util.SecurityUtil;
import net.hkpark.cockstalgia.core.vo.MemberIdentityKeyBaseVo;
import net.hkpark.kakao.developers.dto.response.KakaoAccountDto;
import net.hkpark.kakao.developers.dto.response.KakaoUserInfoDto;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOAuth2Service extends OAuth2Service {
    private final KakaoDeveloperApiService kakaoDeveloperApiService;

    @Override
    public MemberIdentityKeyBaseVo getKeyBaseVoFromAttributes(Map<String, Object> userAttributes) {
        KakaoUserInfoDto userInfoDto = JsonUtil.convertMapToObjectClass(userAttributes, KakaoUserInfoDto.class);
        KakaoAccountDto kakaoAccountDto = userInfoDto.getKakaoAccount();
        Map<String, Object> properties = userInfoDto.getProperties();
        String realname = (String)properties.get("realname");
        String birthday = kakaoAccountDto.getBirthday();
        return MemberIdentityKeyBaseVo.builder().realname(realname).birthday(birthday).build();
    }

    @Override
    public String getUserKeyFromAttributes(Map<String, Object> userAttributes) {
        MemberIdentityKeyBaseVo memberIdentityKeyBaseVo = getKeyBaseVoFromAttributes(userAttributes);
        return SecurityUtil.encodeUserKey(memberIdentityKeyBaseVo);
    }

    @Override
    public boolean isNeedMoreInformation(Map<String, Object> userAttributes) {
        String realname = (String)((Map<?, ?>)userAttributes.get("properties")).get("realname");
        return StringUtils.isEmpty(realname);
    }

    @Override
    public void saveBaseVoToAuthServer(MemberIdentityKeyBaseVo baseVo, OAuth2AuthenticationToken authentication) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("realname", baseVo.getRealname());
        kakaoDeveloperApiService.updateProperties(properties, OAuth2Util.getAccessTokenString(authentication));
    }
}
