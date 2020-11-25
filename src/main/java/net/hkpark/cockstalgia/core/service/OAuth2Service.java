package net.hkpark.cockstalgia.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.core.vo.MemberIdentityKeyBaseVo;
import net.hkpark.kakao.developers.dto.response.KakaoAccountDto;
import net.hkpark.kakao.developers.dto.response.KakaoUserInfoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class OAuth2Service {
    public abstract MemberIdentityKeyBaseVo getKeyBaseVoFromAttributes(Map<String, Object> userAttributes);
    public abstract String getUserKeyFromAttributes(Map<String, Object> userAttributes);
    public abstract boolean isNeedMoreInformation(Map<String, Object> userAttributes);
    public void saveBaseVoToAuthServer(MemberIdentityKeyBaseVo baseVo, OAuth2AuthenticationToken authentication) { }
}
