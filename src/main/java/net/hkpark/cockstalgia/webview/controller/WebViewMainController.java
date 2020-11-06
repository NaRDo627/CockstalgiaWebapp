package net.hkpark.cockstalgia.webview.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.core.exception.BusinessException;
import net.hkpark.cockstalgia.core.vo.MemberIdentityKeyBaseVo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class WebViewMainController {
    /**
     * 회원가입 창 (비공개 - 세션에 값이 있을때만 접근 가능)
     */
    @GetMapping(value = {"/register"})
    public String register(HttpSession session) {
        if (session.getAttribute("memberIdentityKeyBaseVo") == null) {
            throw new BusinessException("exit");
        }

        return "register";
    }

    /**
     * 회원등록 (비공개 - 세션에 값이 있을때만 접근 가능)
     */
    @PostMapping(value = {"/register"})
    public String registerPost(HttpSession session,
                               @RequestParam(value = "realname", required = false) String realname,
                               @RequestParam(value = "birthday", required = false) String birthday) {
        MemberIdentityKeyBaseVo baseVo = (MemberIdentityKeyBaseVo)session.getAttribute("memberIdentityKeyBaseVo");
        if (baseVo == null) {
            throw new BusinessException("exit");
        }
        session.removeAttribute("memberIdentityKeyBaseVo");


        return "register";
    }
}
