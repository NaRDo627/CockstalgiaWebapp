package net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.cocktail.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.cocktail.service.ChatbotCocktailService;
import net.hkpark.cockstalgia.core.annotation.PrintArguments;
import net.hkpark.cockstalgia.core.dto.ResultDto;
import net.hkpark.kakao.openbuilder.dto.request.SkillRequestDto;
import net.hkpark.kakao.openbuilder.dto.response.SkillResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatbot/cocktail")
public class CocktailBasicController {
    private final ChatbotCocktailService chatbotCocktailService;

    @PrintArguments
    @PostMapping(value = "/description/v1")
    public ResponseEntity<?> description(@RequestBody SkillRequestDto skillRequestDto) {
        return ResponseEntity.ok(ResultDto.builder().data(new Date().getTime()).build());
    }

    @PostMapping(value = "/base/v1")
    public ResponseEntity<?> base(@RequestBody SkillRequestDto skillRequestDto) {
        SkillResponseDto responseDto = chatbotCocktailService.getBaseRecipeLists(skillRequestDto);
        return ResponseEntity.ok(responseDto);
    }
}
