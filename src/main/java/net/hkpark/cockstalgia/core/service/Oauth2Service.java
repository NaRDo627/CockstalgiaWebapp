package net.hkpark.cockstalgia.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.core.vo.MemberIdentityKeyBaseVo;
import net.hkpark.kakao.developers.dto.response.KakaoAccountDto;
import net.hkpark.kakao.developers.dto.response.KakaoUserInfoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class Oauth2Service {
    public abstract String getUserKeyFromAttributes(Map<String, Object> userAttributes);
}
