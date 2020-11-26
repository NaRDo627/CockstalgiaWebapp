package net.hkpark.cockstalgia.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.core.exception.BusinessException;
import net.hkpark.cockstalgia.core.util.JsonUtil;
import net.hkpark.cockstalgia.core.util.RestClientUtil;
import net.hkpark.kakao.developers.dto.response.KakaoUserInfoDto;
import net.hkpark.kakao.developers.dto.response.Oauth2TokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoDeveloperApiService {
    private static final String KAKAO_USER_ME_URL = "https://kapi.kakao.com/v2/user/me";
    private static final String KAKAO_UPDATE_PROFILE_URL = "https://kapi.kakao.com/v1/user/update_profile";

    public KakaoUserInfoDto getUserInformation(String accessToken) {
        return RestClientUtil.exchange(KAKAO_USER_ME_URL, HttpMethod.GET,
                getAuthorizationHeaderEntity(accessToken), KakaoUserInfoDto.class);
    }

    public Map updateProperties(Map<String, Object> properties, String accessToken) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        try {
            parameters.add("properties", JsonUtil.convertObjectToJsonString(properties));
        } catch (JsonProcessingException e) {
            log.error("update profile failed while convert object to Json", e);
            throw new BusinessException(e);
        }

        HttpEntity<?> httpEntity = getAuthorizationHeaderEntityWithBody(accessToken, parameters);
        return RestClientUtil.exchange(KAKAO_UPDATE_PROFILE_URL, HttpMethod.POST, httpEntity, Map.class);
    }

    private HttpEntity<?> getAuthorizationHeaderEntity(String accessToken) {
        return getAuthorizationHeaderEntityWithBody(accessToken, null);
    }

    private HttpEntity<?> getAuthorizationHeaderEntityWithBody(String accessToken, MultiValueMap<String, Object> parameters) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        return new HttpEntity<>(parameters, headers);
    }
}
