package net.hkpark.cockstalgia.core.service;

import lombok.RequiredArgsConstructor;
import net.hkpark.cockstalgia.core.entity.Member;
import net.hkpark.cockstalgia.core.repository.MemberRepository;
import net.hkpark.cockstalgia.core.security.SocialType;
import net.hkpark.cockstalgia.core.util.SecurityUtil;
import net.hkpark.cockstalgia.core.vo.MemberIdentityKeyBaseVo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final OAuth2ServiceFactory oauth2ServiceFactory;
    private final MemberEntityService memberEntityService;
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Set<GrantedAuthority> authorities = new HashSet<>(oAuth2User.getAuthorities());
        Map<String, Object> userAttributes = getUserAttributes(oAuth2User.getAttributes());
        authorities.add(new OAuth2UserAuthority(userAttributes));

        SocialType socialType = SocialType.ofClientRegistrationId(userRequest.getClientRegistration().getRegistrationId());
        OAuth2Service oAuth2Service = oauth2ServiceFactory.getInstanceBySocialType(socialType);

        if (oAuth2Service.isNeedMoreInformation(userAttributes)) {
            authorities.add(new SimpleGrantedAuthority("NEED_MORE_INFO"));
            return new DefaultOAuth2User(authorities, userAttributes, userRequest.getClientRegistration().getProviderDetails()
                    .getUserInfoEndpoint().getUserNameAttributeName());
        }

        String userKey = oAuth2Service.getUserKeyFromAttributes(userAttributes);
        String userName = (String)((Map<?, ?>)userAttributes.get("properties")).get("nickname");
        Member member = memberRepository.findByMemberIdentityKeyAndIsActive(userKey, true)
                .orElseGet(() -> newMember(userName, userKey));

        if (member.isAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

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

    private Member newMember(String userName, String userKey) {
        Member newMember = Member.builder()
                .name(userName)
                .memberIdentityKey(userKey)
                .isActive(true)
                .build();
        return memberEntityService.saveMember(newMember);
    }
}
