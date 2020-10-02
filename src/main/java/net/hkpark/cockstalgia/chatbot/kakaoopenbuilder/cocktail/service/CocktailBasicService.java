package net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.cocktail.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.core.constant.LiquorType;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.util.BasicCardUtil;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.util.SkillResponseUtil;
import net.hkpark.cockstalgia.core.constant.ErrorMessage;
import net.hkpark.cockstalgia.core.exception.InvalidValueException;
import net.hkpark.kakao.openbuilder.dto.request.ActionDto;
import net.hkpark.kakao.openbuilder.dto.request.SkillRequestDto;
import net.hkpark.kakao.openbuilder.dto.response.BasicCardDto;
import net.hkpark.kakao.openbuilder.dto.response.ButtonDto;
import net.hkpark.kakao.openbuilder.dto.response.QuickReplyDto;
import net.hkpark.kakao.openbuilder.dto.response.SkillResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class CocktailBasicService {
    public SkillResponseDto getBaseRecipeLists(SkillRequestDto skillRequestDto, String baseName) {
        LiquorType liquorType = LiquorType.of(baseName.toUpperCase());
        SkillResponseDto skillResponseDto;
        switch (liquorType) {
            case WHISKEY:
                skillResponseDto = getWhiskeyRecipes();
                break;

            default:
                throw new UnsupportedOperationException();
        }
        return skillResponseDto;
    }

    public SkillResponseDto getReceipe(SkillRequestDto skillRequestDto) {
        // find cocktail by alias
        ActionDto actionDto = skillRequestDto.getAction();
        if (StringUtils.isEmpty(actionDto.getParams().get("cocktail_name"))) {
            throw new InvalidValueException(ErrorMessage.REQUEST_BAD_REQUEST);
        }

        // if not exists, throw

        // return receipe card
        BasicCardDto basicCard = BasicCardUtil.getOne("맨해튼(Manhatan)", "맨해튼은 자고로 '맨햍은'으로 발음해줘야 한다. 미국 맨햍은의 저녁, 빌딩 창문을 통해 바라보는 노을이 생각나는 칵테일이다. 높은 도수와 깊은 맛으로 칵테일의 여왕이라 불리는 술.\n" +
                "1. 버번 위스키 1.5oz\n" +
                "2. 스윗 베르무트 3/4oz\n" +
                "3. 앙고스투라 비터 1dash\n" +
                "4. 체리 가니쉬", "http://k.kakaocdn.net/dn/NsIko/btqJsjQVymO/ErBGk0suKWQKYRnsjOQsBk/800x800.jpg");
        ButtonDto button = ButtonDto.builder().label("뒤로 가기").action("message").messageText("위스키 칵테일 알아보기").build();
        return SkillResponseUtil.basicCardResponseDto(basicCard, button);
    }

    private SkillResponseDto getWhiskeyRecipes() {
        QuickReplyDto reply = QuickReplyDto.builder()
                .label("맨해튼")
                .action("message")
                .messageText("맨해튼 칵테일")
//                .blockId("5f7423dab611b73b27bbaa4d")
                .build();

        return SkillResponseUtil.simpleTextWithQuickReplies("위스키 칵테일을 선택해 주세요.", reply);
    }
}
