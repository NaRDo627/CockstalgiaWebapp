package net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.cocktail.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.cocktail.constant.CocktailBase;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.util.BasicCardUtil;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.util.SkillResponseUtil;
import net.hkpark.cockstalgia.core.constants.ErrorMessage;
import net.hkpark.cockstalgia.core.exception.InvalidValueException;
import net.hkpark.kakao.openbuilder.dto.request.ActionDto;
import net.hkpark.kakao.openbuilder.dto.request.SkillRequestDto;
import net.hkpark.kakao.openbuilder.dto.response.BasicCardDto;
import net.hkpark.kakao.openbuilder.dto.response.SkillResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CocktailBasicService {
    public SkillResponseDto getBaseRecipes(SkillRequestDto skillRequestDto, String baseName) {
        CocktailBase cocktailBase = CocktailBase.of(baseName.toUpperCase());
        SkillResponseDto skillResponseDto;
        switch (cocktailBase) {
            case WHISKEY:
                skillResponseDto = getWhiskeyRecipes();
                break;

            default:
                throw new UnsupportedOperationException();
        }
        return skillResponseDto;
    }

    private SkillResponseDto getWhiskeyRecipes() {
        List<BasicCardDto> basicCards = Arrays.asList(
                BasicCardUtil.getOne("맨해튼(Manhatan)", "맨해튼은 자고로 '맨햍은'으로 발음해줘야 한다. 미국 맨햍은의 저녁, 빌딩 창문을 통해 바라보는 노을이 생각나는 칵테일이다. 높은 도수와 깊은 맛으로 칵테일의 여왕이라 불리는 술.\n" +
                        "1. 버번 위스키 1.5oz\n" +
                        "2. 스윗 베르무트 3/4oz\n" +
                        "3. 앙고스투라 비터 1dash\n" +
                        "4. 체리 가니쉬", "http://k.kakaocdn.net/dn/NsIko/btqJsjQVymO/ErBGk0suKWQKYRnsjOQsBk/800x800.jpg"),
                BasicCardUtil.getOne("맨해튼(Manhatan)2", "맨해튼은 자고로 '맨햍은'으로 발음해줘야 한다. 미국 맨햍은의 저녁, 빌딩 창문을 통해 바라보는 노을이 생각나는 칵테일이다. 높은 도수와 깊은 맛으로 칵테일의 여왕이라 불리는 술.\n" +
                        "1. 버번 위스키 1.5oz\n" +
                        "2. 스윗 베르무트 3/4oz\n" +
                        "3. 앙고스투라 비터 1dash\n" +
                        "4. 체리 가니쉬", "http://k.kakaocdn.net/dn/NsIko/btqJsjQVymO/ErBGk0suKWQKYRnsjOQsBk/800x800.jpg")
        );

        return SkillResponseUtil.basicCardCarouselResponseDto(basicCards);
    }
}
