package net.hkpark.cockstalgia.core.security;


//import com.kakaointerntask.bank.common.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
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

    public LoginSuccessHandler(OAuth2ServiceFactory oauth2ServiceFactory) {
        this.oauth2ServiceFactory = oauth2ServiceFactory;
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

        if (SecurityUtil.hasRole(authentication.getAuthorities(), "NEED_MORE_INFO")) {
            SocialType socialType =
                    SocialType.ofClientRegistrationId(auth2AuthenticationToken.getAuthorizedClientRegistrationId());
            OAuth2Service oauth2Service = oauth2ServiceFactory.getInstanceBySocialType(socialType);
            OAuth2User user = (OAuth2User)authentication.getPrincipal();
            MemberIdentityKeyBaseVo memberIdentityKeyBaseVo = oauth2Service.getKeyBaseVoFromAttributes(user.getAttributes());
            session.setAttribute("memberIdentityKeyBaseVo", memberIdentityKeyBaseVo);
            getRedirectStrategy().sendRedirect(request, response, "/register");
            return;
        }

        /* 만약 다른 페이지에서 로그인 페이지로 넘어왔다면 해당 페이지로 리다이렉트 */
//        Optional<String> optRedirectUrl = Optional.ofNullable((String)session.getAttribute("prevPage"));
//        String redirectUrl = optRedirectUrl.orElse("");
//        if (redirectUrl.equals("/login")) {
//            session.removeAttribute("prevPage");
//            getRedirectStrategy().sendRedirect(request, response, redirectUrl);
//            return;
//        }

        if (SecurityUtil.hasRole(authentication.getAuthorities(), "ROLE_ADMIN")) {
            getRedirectStrategy().sendRedirect(request, response, "/admin");
            return;
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}