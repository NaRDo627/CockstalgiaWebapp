package net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.cocktail.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.util.BasicCardUtil;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.util.SkillResponseUtil;
import net.hkpark.cockstalgia.core.constant.ErrorMessage;
import net.hkpark.cockstalgia.core.constant.LiquorType;
import net.hkpark.cockstalgia.core.entity.Cocktail;
import net.hkpark.cockstalgia.core.exception.EntityNotFoundException;
import net.hkpark.cockstalgia.core.exception.InvalidValueException;
import net.hkpark.cockstalgia.core.repository.CocktailRepository;
import net.hkpark.kakao.openbuilder.dto.request.ActionDto;
import net.hkpark.kakao.openbuilder.dto.request.SkillRequestDto;
import net.hkpark.kakao.openbuilder.dto.request.ValidationRequestDto;
import net.hkpark.kakao.openbuilder.dto.request.ValueDto;
import net.hkpark.kakao.openbuilder.dto.response.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatbotCocktailValidationService {
    private final CocktailRepository cocktailRepository;

    public ValidationResponseDto validate(ValidationRequestDto requestDto) {
        String name = requestDto.getValue().getResolved();
        if (! cocktailRepository.findByNameContaining(name).isPresent()) {
            return ValidationResponseDto.builder()
                    .status("FAIL")
                    .build();
        }

        return ValidationResponseDto.builder()
                .status("SUCCESS")
                .value(name)
                .build();
    }
}
