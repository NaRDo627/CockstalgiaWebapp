package net.hkpark.cockstalgia.core.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.core.annotation.PrintArguments;
import net.hkpark.cockstalgia.core.dto.ResultDto;
import net.hkpark.cockstalgia.core.exception.InvalidValueException;
import net.hkpark.cockstalgia.core.repository.CocktailRepository;
import net.hkpark.cockstalgia.core.service.Oauth2Service;
import net.hkpark.cockstalgia.core.vo.MemberIdentityKeyBaseVo;
import net.hkpark.cockstalgia.webview.admin.service.WebViewAdminService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/oauth2")
public class Oauth2Controller {
    private final Oauth2Service oauth2Service;

    /**
     * 카카오 oauth2 엔드포인트
     */
    @GetMapping(value = {"/kakao"})
    public String oauth2Kakao(Model model) {
        return "redirect:" + oauth2Service.getKakaoLoginRedirectUrl();
    }

    /**
     * 카카오 oauth2 콜백전용
     */
    @PrintArguments
    @GetMapping(value = {"/kakao/callback"})
    public String oauth2KakaoCallback(@RequestParam(value = "code", required = false) String code,
                                      @RequestParam(value = "error", required = false) String error,
                                      HttpSession session) {
        if (! StringUtils.isEmpty(error)) {
            return "redirect:/admin/login";
        }

        throwIfValueIsEmpty(code);
        MemberIdentityKeyBaseVo baseVo = oauth2Service.getMemberIdentityKeyBaseVoFromKakao(code);
        if (StringUtils.isEmpty(baseVo.getRealname()) || StringUtils.isEmpty(baseVo.getBirthday())) {
            session.setAttribute("memberIdentityKeyBaseVo", baseVo);
            return "redirect:/register";
        }

        return "redirect:/admin";
    }

    private void throwIfValueIsEmpty(Object value) {
        if (StringUtils.isEmpty(value)) {
            throw new InvalidValueException();
        }
    }
}
