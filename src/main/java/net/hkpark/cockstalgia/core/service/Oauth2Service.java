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
@Service
@RequiredArgsConstructor
public class Oauth2Service {
    private static final String KAKAO_AUTHORIZE_URL_TEMPLATE = "https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code";

    private final KakaoDeveloperApiService kakaoDeveloperApiService;

    @Value("${kakao.restapi.secret}")
    private String kakaoRestapiSecret;

    @Value("${kakao.client.secret}")
    private String kakaoClientSecret;

    @Value("${app.public.access.url}")
    private String appPublicAccessUrl;

    public String getKakaoLoginRedirectUrl() {
        return String.format(KAKAO_AUTHORIZE_URL_TEMPLATE, kakaoRestapiSecret, appPublicAccessUrl + "oauth2/kakao/callback");
    }

    public MemberIdentityKeyBaseVo getMemberIdentityKeyBaseVoFromKakao(String kakaoAuthCode) {
        //
        String accessToken = kakaoDeveloperApiService.getKakaoToken(kakaoAuthCode).getAccessToken();
        KakaoUserInfoDto userInfoDto = kakaoDeveloperApiService.getUserInformation(accessToken);
        KakaoAccountDto kakaoAccountDto = userInfoDto.getKakaoAccount();
        Map<String, Object> properties = userInfoDto.getProperties();
        String realname = (String)properties.get("realname");
        String birthday = kakaoAccountDto.getBirthday();
        return MemberIdentityKeyBaseVo.builder().realname(realname).birthday(birthday).build();
//
//
//
//        String memberIdentityKeyBaseString =  userInfoDto.getKakaoAccount().getBirthday();
//        Map<String, Object> test = new HashMap<>();
//        test.put("realname", "박현국");
//        Map<String, Object> result = kakaoDeveloperApiService.updateProperties(test, accessToken);
//
//        // if not exists, try register
//
//        return "";
    }


}
