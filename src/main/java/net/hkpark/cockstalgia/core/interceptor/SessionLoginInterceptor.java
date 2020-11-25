package net.hkpark.cockstalgia.core.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.core.service.MemberEntityService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionLoginInterceptor extends HandlerInterceptorAdapter {
    private final MemberEntityService memberEntityService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod))
            return super.preHandle(request, response, handler);

        if (request.getUserPrincipal() == null) {
            response.sendRedirect("/login");
            return false;
        }
//
//        HttpSession httpSession = request.getSession();
//        Object objBankUser = httpSession.getAttribute("currentMember");
//        OAuth2User oAuth2User = (OAuth2User)request.getUserPrincipal();
//
//        String authId = request.getUserPrincipal().getName();
//        if (!(objBankUser instanceof BankUser) || !((BankUser) objBankUser).getId().equals(authId)) {
//            BankUser bankUser = bankUserService.findBankUserById(authId);
//            httpSession.setAttribute("bankUser", bankUser);
//            log.info(String.format("Session info was set for id : \"%s\"", authId));
//        }

        return super.preHandle(request, response, handler);
    }
}