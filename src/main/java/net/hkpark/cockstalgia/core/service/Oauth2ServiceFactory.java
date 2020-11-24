package net.hkpark.cockstalgia.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.core.exception.InvalidValueException;
import net.hkpark.cockstalgia.core.security.SocialType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class Oauth2ServiceFactory {
    private final KakaoOauth2Service kakaoOauth2Service;

    public Oauth2Service getInstanceBySocialType(SocialType socialType) {
        switch (socialType) {
            case KAKAO:
                return this.kakaoOauth2Service;
        }

        throw new InvalidValueException();
    }
}
