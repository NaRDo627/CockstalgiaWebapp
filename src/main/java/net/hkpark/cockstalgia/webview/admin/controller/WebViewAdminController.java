package net.hkpark.cockstalgia.webview.admin.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.member.service.MemberService;
import net.hkpark.cockstalgia.core.annotation.PrintArguments;
import net.hkpark.cockstalgia.core.dto.ResultDto;
import net.hkpark.cockstalgia.core.repository.CocktailRepository;
import net.hkpark.cockstalgia.core.service.CocktailDataService;
import net.hkpark.cockstalgia.core.util.FileUtil;
import net.hkpark.cockstalgia.webview.admin.service.WebViewAdminService;
import net.hkpark.cockstalgia.webview.core.service.WebViewCoreService;
import net.hkpark.kakao.openbuilder.dto.request.SkillRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class WebViewAdminController {
    private final WebViewAdminService webViewAdminService;
    private final CocktailRepository cocktailRepository;

    /**
     * 관리자 페이지 메인
     */
    @GetMapping(value = {"", "/"})
    public String index(Model model) {
        model.addAttribute("cocktailCount", cocktailRepository.count());
        return "admin/index";
    }

    /**
     * 칵테일 관리
     */
    @GetMapping(value = {"/login"})
    public String login(HttpServletRequest request, Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()){
//            if (SecurityUtil.hasRole(authentication.getAuthorities(), "ROLE_ADMIN"))
//                return "redirect:/admin";
            return "redirect:/admin";
        }

        String referer = request.getHeader("Referer");
        request.getSession().setAttribute("prevPage", referer);
        return "admin/login";
    }

    /**
     * 칵테일 관리
     */
    @GetMapping(value = {"/cocktail"})
    public String cocktail(Model model) {
        return "admin/cocktail";
    }

    /**
     * 회원 관리
     */
    @GetMapping(value = {"/member"})
    public String member(Model model) {
        return "admin/member";
    }

    /**
     * 칵테일 베이스 리스트 조회 (/cocktail 페이지에서 사용)
     */
    @GetMapping(value = {"/liquor-bases"})
    @ResponseBody
    public List<String> liquorBases() {
        return webViewAdminService.getLiquorBases();
    }

    @PostMapping(value = {"/upload-cocktail"})
    @ResponseBody
    public ResultDto uploadImage(@RequestParam("cocktail-image") MultipartFile cocktailImageFile) {
        return webViewAdminService.saveMultipartFile(cocktailImageFile);
    }
}
