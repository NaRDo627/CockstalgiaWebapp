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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod))
            return super.preHandle(request, response, handler);

        if (! isMemberAuthenticated(request)) {
            response.sendRedirect("/admin/login");
            return false;
        }

        return super.preHandle(request, response, handler);
    }

    private boolean isMemberAuthenticated(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        return httpSession.getAttribute("currentMember") != null;
    }
}