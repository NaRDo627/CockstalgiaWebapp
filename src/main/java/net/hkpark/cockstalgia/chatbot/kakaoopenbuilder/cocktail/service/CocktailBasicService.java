package net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.cocktail.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.core.constant.LiquorType;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.util.BasicCardUtil;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.util.SkillResponseUtil;
import net.hkpark.cockstalgia.core.constant.ErrorMessage;
import net.hkpark.cockstalgia.core.entity.Cocktail;
import net.hkpark.cockstalgia.core.exception.EntityAlreadyExistsException;
import net.hkpark.cockstalgia.core.exception.EntityNotFoundException;
import net.hkpark.cockstalgia.core.exception.InvalidValueException;
import net.hkpark.cockstalgia.core.repository.CocktailRepository;
import net.hkpark.kakao.openbuilder.dto.request.ActionDto;
import net.hkpark.kakao.openbuilder.dto.request.SkillRequestDto;
import net.hkpark.kakao.openbuilder.dto.response.BasicCardDto;
import net.hkpark.kakao.openbuilder.dto.response.ButtonDto;
import net.hkpark.kakao.openbuilder.dto.response.QuickReplyDto;
import net.hkpark.kakao.openbuilder.dto.response.SkillResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CocktailBasicService {
    private final CocktailRepository cocktailRepository;

    public SkillResponseDto getBaseRecipeLists(SkillRequestDto skillRequestDto) {
        ActionDto actionDto = skillRequestDto.getAction();
        if (StringUtils.isEmpty(actionDto.getParams().get("base_name"))) {
            throw new InvalidValueException(ErrorMessage.REQUEST_BAD_REQUEST);
        }
        String baseName = actionDto.getParams().get("base_name").toString();
        LiquorType base = LiquorType.of(baseName.toUpperCase());
        List<Cocktail> cocktailList = cocktailRepository.findAllByBase(base);
        List<QuickReplyDto> quickReplyDtos = new ArrayList<>();
        for (Cocktail cocktail : cocktailList) {
            QuickReplyDto reply = QuickReplyDto.builder()
                    .label(cocktail.getName())
                    .action("message")
                    .messageText(cocktail.getName() + " 알려줘")
//                .blockId("5f0478173210ac0001404019")
                    .build();
            quickReplyDtos.add(reply);
        }

        return SkillResponseUtil.simpleTextWithQuickReplies("칵테일을 선택해 주세요.",
                quickReplyDtos.toArray(new QuickReplyDto[0]));
    }

    public SkillResponseDto getRecipe(SkillRequestDto skillRequestDto) {
        // find cocktail by alias
        ActionDto actionDto = skillRequestDto.getAction();
        if (StringUtils.isEmpty(actionDto.getParams().get("cocktail_name"))) {
            throw new InvalidValueException(ErrorMessage.REQUEST_BAD_REQUEST);
        }

        // if not exists, throw
        String cocktailName = actionDto.getParams().get("cocktail_name").toString();
        Cocktail cocktail = cocktailRepository.findByNameContaining(cocktailName).orElseThrow(EntityNotFoundException::new);
        String simpleRecipe = cocktail.getSimpleRecipe() == null ? "" : cocktail.getSimpleRecipe();
        String description = cocktail.getDescription() + "\n" + simpleRecipe;

        // return recipe card
        BasicCardDto basicCard = BasicCardUtil.getOne(cocktail.getName(), description, cocktail.getImageUrl());
        ButtonDto button = ButtonDto.builder().label("뒤로 가기")
                .action("message").messageText(cocktail.getBase().getKoreanName() + " 칵테일 알아보기").build();

        return SkillResponseUtil.basicCardResponseDto(basicCard, button);
    }
}
