package net.hkpark.cockstalgia.core.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorPageController implements ErrorController {
    private static final String PATH = "/error"; // configure 에서 Redirect 될 path

    @GetMapping(value = PATH)
    public String error(HttpServletRequest request) {
        return getErrorPagePath(request);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

    private String getErrorPagePath(HttpServletRequest request) {
        String originalUri = (String) request.getAttribute(
                RequestDispatcher.FORWARD_REQUEST_URI);
        if (originalUri.startsWith("/admin")) {
            return getAdminErrorPagePath(request);
        }
        return "/error/404";
    }

    private String getAdminErrorPagePath(HttpServletRequest request) {
        Integer status = (Integer)request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        switch (status) {
            case 403:
                return "/admin/error/403";
            case 500:
                return "/admin/error/500";
            default:
                return "/admin/error/404";
        }
    }
}
