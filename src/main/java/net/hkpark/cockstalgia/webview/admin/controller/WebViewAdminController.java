package net.hkpark.cockstalgia.webview.admin.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.member.service.MemberService;
import net.hkpark.cockstalgia.core.annotation.PrintArguments;
import net.hkpark.cockstalgia.core.dto.ResultDto;
import net.hkpark.cockstalgia.webview.admin.service.WebViewAdminService;
import net.hkpark.kakao.openbuilder.dto.request.SkillRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class WebViewAdminController {
    private final WebViewAdminService webViewAdminService;

    @GetMapping(value = {"", "/"})
    public String index(Model model) {
        return "admin/index";
    }

    @GetMapping(value = {"/cocktail"})
    public String cocktail(Model model) {
        return "admin/cocktail";
    }

    @GetMapping(value = {"/liquor-bases"})
    @ResponseBody
    public List<String> liquorBases() {
        return webViewAdminService.getLiquorBases();
    }
}
