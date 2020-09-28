package net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.cocktail.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.cocktail.service.CocktailBasicService;
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
    private final CocktailBasicService cocktailBasicService;

    @PrintArguments
    @RequestMapping(value = "/description/v1", method = RequestMethod.POST)
    public ResponseEntity<?> description(@RequestBody SkillRequestDto skillResultDto) {
        return ResponseEntity.ok(ResultDto.builder().data(new Date().getTime()).build());
    }

    @RequestMapping(value = "/{baseName}/base/v1", method = RequestMethod.POST)
    public ResponseEntity<?> base(@RequestBody SkillRequestDto skillResultDto, @PathVariable("baseName") String baseName) {
        SkillResponseDto responseDto = cocktailBasicService.getBaseRecipes(skillResultDto, baseName);
        return ResponseEntity.ok(responseDto);
    }
}