package net.hkpark.cockstalgia.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.core.exception.InvalidValueException;
import net.hkpark.cockstalgia.core.security.SocialType;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2ServiceFactory {
    private final KakaoOAuth2Service kakaoOauth2Service;

    public OAuth2Service getInstanceBySocialType(SocialType socialType) {
        switch (socialType) {
            case KAKAO:
                return this.kakaoOauth2Service;
        }

        throw new InvalidValueException();
    }
}
