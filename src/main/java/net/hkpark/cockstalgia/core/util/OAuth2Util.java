package net.hkpark.cockstalgia.core.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;

public class OAuth2Util {
    private OAuth2Util() { }

    public static String getAccessTokenString(OAuth2AuthenticationToken authentication) {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        final OAuth2AuthorizedClientService authorizedClientService =
                applicationContext.getBean(OAuth2AuthorizedClientService.class);

        OAuth2AuthorizedClient authorizedClient =
                authorizedClientService.loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());

        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        return accessToken.getTokenValue();
    }
}
