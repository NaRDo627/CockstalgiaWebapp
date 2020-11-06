package net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.cocktail.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.cocktail.service.ChatbotCocktailValidationService;
import net.hkpark.cockstalgia.core.annotation.PrintArguments;
import net.hkpark.kakao.openbuilder.dto.request.ValidationRequestDto;
import net.hkpark.kakao.openbuilder.dto.response.ValidationResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatbot/cocktail/validation")
public class CocktailValidationController {
    private final ChatbotCocktailValidationService chatbotCocktailValidationService;

    @PrintArguments
    @PostMapping(value = "/v1")
    public ResponseEntity<?> description(@RequestBody ValidationRequestDto validationRequestDto) {
        ValidationResponseDto responseDto = chatbotCocktailValidationService.validate(validationRequestDto);
        return ResponseEntity.ok(responseDto);
    }

}
