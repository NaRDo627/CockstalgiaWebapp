package net.hkpark.cockstalgia.core.service;

import lombok.RequiredArgsConstructor;
import net.hkpark.cockstalgia.core.entity.Member;
import net.hkpark.cockstalgia.core.exception.EntityNotFoundException;
import net.hkpark.cockstalgia.core.repository.MemberRepository;
import net.hkpark.cockstalgia.core.security.SocialType;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequestEntityConverter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final Oauth2ServiceFactory oauth2ServiceFactory;
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Set<GrantedAuthority> authorities = new HashSet<>(oAuth2User.getAuthorities());
        Map<String, Object> userAttributes = getUserAttributes(oAuth2User.getAttributes());
        authorities.add(new OAuth2UserAuthority(userAttributes));

        String realname = (String)((Map<?, ?>)userAttributes.get("properties")).get("realname");
        if (StringUtils.isEmpty(realname)) {
            authorities.add(new SimpleGrantedAuthority("NEED_MORE_INFO"));
            return new DefaultOAuth2User(authorities, userAttributes, userRequest.getClientRegistration().getProviderDetails()
                    .getUserInfoEndpoint().getUserNameAttributeName());
        }


        // realname 속성 없으면 추가 input 페이지로 이동
//        SocialType socialType = SocialType.of(userRequest.getClientRegistration().getRegistrationId());
//        Oauth2Service oauth2Service = oauth2ServiceFactory.getInstanceBySocialType(socialType);
//        String userKey = oauth2Service.getUserKeyFromAttributes(userAttributes);
//        Member member = memberRepository.findByMemberIdentityKeyAndIsActive(userKey, true).orElseThrow(EntityNotFoundException::new);
//
//        if (member.isAdmin()) {
//            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        }

        return new DefaultOAuth2User(authorities, userAttributes, userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName());
    }

    // 네이버는 HTTP response body에 response 안에 id 값을 포함한 유저정보를 넣어주므로 유저정보를 빼내기 위한 작업을 함
    private Map<String, Object> getUserAttributes(Map<String, Object> userAttributes) {
        if(userAttributes.containsKey("response")) {
            LinkedHashMap responseData = (LinkedHashMap)userAttributes.get("response");
            userAttributes.putAll(responseData);
            userAttributes.remove("response");
        }
        return userAttributes;
    }
}
