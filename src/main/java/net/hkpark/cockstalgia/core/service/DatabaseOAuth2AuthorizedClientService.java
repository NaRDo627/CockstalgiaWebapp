//package net.hkpark.cockstalgia.core.service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import net.hkpark.cockstalgia.core.security.SocialType;
//import net.hkpark.cockstalgia.core.util.SecurityUtil;
//import net.hkpark.cockstalgia.core.vo.MemberIdentityKeyBaseVo;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientId;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;
//
//import java.util.Map;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class DatabaseOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {
//    private final MemberEntityService memberEntityService;
//    private final OAuth2ServiceFactory oAuth2ServiceFactory;
//
//    @Override
//    public void saveAuthorizedClient(OAuth2AuthorizedClient oAuth2AuthorizedClient, Authentication authentication) {
//        if (! SecurityUtil.hasRole(authentication.getAuthorities(), "MEMBER_SAVE_NEEDED")) {
//            return;
//        }
//
//        SocialType socialType = SocialType.ofClientRegistrationId(
//                oAuth2AuthorizedClient.getClientRegistration().getRegistrationId());
//        OAuth2Service oAuth2Service = oAuth2ServiceFactory.getInstanceBySocialType(socialType);
//        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
//        MemberIdentityKeyBaseVo baseVo = oAuth2Service.getKeyBaseVoFromAttributes(oAuth2User.getAttributes());
//        memberEntityService.saveMemberWithKeyBaseVo(baseVo);
//    }
//
//    @Override
//    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String s, String s1) {
//        throw new NotImplementedException();
//    }
//
//    @Override
//    public void removeAuthorizedClient(String s, String s1) {
//        throw new NotImplementedException();
//    }
//}
