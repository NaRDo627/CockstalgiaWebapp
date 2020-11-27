package net.hkpark.cockstalgia.core.security;


//import com.kakaointerntask.bank.common.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.core.entity.Member;
import net.hkpark.cockstalgia.core.exception.EntityNotFoundException;
import net.hkpark.cockstalgia.core.repository.MemberRepository;
import net.hkpark.cockstalgia.core.service.MemberEntityService;
import net.hkpark.cockstalgia.core.service.OAuth2Service;
import net.hkpark.cockstalgia.core.service.OAuth2ServiceFactory;
import net.hkpark.cockstalgia.core.util.SecurityUtil;
import net.hkpark.cockstalgia.core.vo.MemberIdentityKeyBaseVo;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final OAuth2ServiceFactory oauth2ServiceFactory;
    private final MemberRepository memberRepository;

    public LoginSuccessHandler(OAuth2ServiceFactory oauth2ServiceFactory, MemberRepository memberRepository) {
        this.oauth2ServiceFactory = oauth2ServiceFactory;
        this.memberRepository = memberRepository;
        setDefaultTargetUrl("/admin");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws ServletException, IOException {
        log.info(String.format("id : \"%s\" logged in successfully", authentication.getName()));

        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
        HttpSession session = request.getSession();
        OAuth2User user = (OAuth2User)authentication.getPrincipal();
        SocialType socialType =
                SocialType.ofClientRegistrationId(auth2AuthenticationToken.getAuthorizedClientRegistrationId());
        OAuth2Service oauth2Service = oauth2ServiceFactory.getInstanceBySocialType(socialType);

        if (SecurityUtil.hasRole(authentication.getAuthorities(), "NEED_MORE_INFO")) {
            MemberIdentityKeyBaseVo memberIdentityKeyBaseVo = oauth2Service.getKeyBaseVoFromAttributes(user.getAttributes());
            session.setAttribute("memberIdentityKeyBaseVo", memberIdentityKeyBaseVo);
            getRedirectStrategy().sendRedirect(request, response, "/register");
            return;
        }

        String userKey = oauth2Service.getUserKeyFromAttributes(user.getAttributes());
        Member member = memberRepository.findByMemberIdentityKeyAndIsActive(userKey, true)
                .orElseThrow(EntityNotFoundException::new);

        session.setAttribute("currentMember", member);
        if (SecurityUtil.hasRole(authentication.getAuthorities(), "ROLE_ADMIN")) {
            getRedirectStrategy().sendRedirect(request, response, "/admin");
            return;
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}