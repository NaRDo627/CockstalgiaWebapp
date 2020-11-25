package net.hkpark.cockstalgia.webview.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.core.exception.BusinessException;
import net.hkpark.cockstalgia.core.security.SocialType;
import net.hkpark.cockstalgia.core.service.MemberEntityService;
import net.hkpark.cockstalgia.core.service.OAuth2Service;
import net.hkpark.cockstalgia.core.service.OAuth2ServiceFactory;
import net.hkpark.cockstalgia.core.vo.MemberIdentityKeyBaseVo;
import net.hkpark.cockstalgia.webview.core.service.WebViewMemberService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class WebViewMainController {
    private final OAuth2ServiceFactory oAuth2ServiceFactory;
    private final MemberEntityService memberEntityService;
    /**
     * 회원가입 창 (비공개 - 세션에 값이 있을때만 접근 가능)
     */
    @GetMapping(value = {"/register"})
    public String register(HttpSession session, Model model) {
        if (session.getAttribute("memberIdentityKeyBaseVo") == null) {
            throw new BusinessException("exit");
        }
        MemberIdentityKeyBaseVo baseVo = (MemberIdentityKeyBaseVo)session.getAttribute("memberIdentityKeyBaseVo");
        model.addAttribute("baseVo", baseVo);
        return "register";
    }

    /**
     * 회원등록 (비공개 - 세션에 값이 있을때만 접근 가능)
     */
    @PostMapping(value = {"/register"})
    public String registerPost(HttpSession session,
                               @RequestParam(value = "realname", required = false) String realname,
                               @RequestParam(value = "birthday", required = false) String birthday,
                               OAuth2AuthenticationToken authentication) {
        MemberIdentityKeyBaseVo baseVoFromSession =
                (MemberIdentityKeyBaseVo)session.getAttribute("memberIdentityKeyBaseVo");
        SocialType socialType = SocialType.ofClientRegistrationId(authentication.getAuthorizedClientRegistrationId());
        if (baseVoFromSession == null) {
            throw new BusinessException("exit");
        }
        session.removeAttribute("memberIdentityKeyBaseVo");
        session.removeAttribute("socialType");

        MemberIdentityKeyBaseVo newBaseVo = MemberIdentityKeyBaseVo.builder()
                .realname(StringUtils.isEmpty(realname) ? baseVoFromSession.getRealname() : realname)
                .birthday(StringUtils.isEmpty(birthday) ? baseVoFromSession.getBirthday() : birthday)
                .build();

        memberEntityService.saveMemberWithKeyBaseVo(newBaseVo);

        OAuth2Service oAuth2Service = oAuth2ServiceFactory.getInstanceBySocialType(socialType);
        oAuth2Service.saveBaseVoToAuthServer(newBaseVo, authentication);

        authentication.setAuthenticated(false);
        return "register-complete";
    }
}
