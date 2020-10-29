package net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.util;

import lombok.NonNull;
import net.hkpark.cockstalgia.core.exception.InvalidValueException;
import net.hkpark.kakao.openbuilder.dto.response.*;

import java.util.Arrays;
import java.util.List;

public class SkillResponseUtil {
    private SkillResponseUtil() { }

    public static SkillResponseDto simpleTextResponseDto(@NonNull String text) {
        SimpleTextDto simpleText = SimpleTextDto.builder().text(text).build();
        ComponentDto component = ComponentDto.builder().simpleText(simpleText).build();
        SkillTemplateDto skillTemplate = SkillTemplateDto.builder().build();
        skillTemplate.getOutputs().add(component);
        return SkillResponseDto.builder().template(skillTemplate).build();
    }

    public static SkillResponseDto basicCardResponseDto(@NonNull String title,
                                                        @NonNull String description,
                                                        @NonNull String thumbnailUrl,
                                                        ButtonDto... buttons) {
        return basicCardResponseDto(title, description, thumbnailUrl, false, 0, 0, buttons);
    }

    public static SkillResponseDto basicCardResponseDto(@NonNull String title,
                                                        @NonNull String description,
                                                        @NonNull String thumbnailUrl,
                                                        boolean thumbnailFixedRatio,
                                                        int width,
                                                        int height,
                                                         ButtonDto... buttons) {
        ThumbnailDto thumbnail = ThumbnailDto.builder()
                .imageUrl(thumbnailUrl)
                .fixedRatio(thumbnailFixedRatio)
                .width(width)
                .height(height)
                .build();

        BasicCardDto basicCard = BasicCardDto.builder()
                .title(title)
                .description(description)
                .thumbnail(thumbnail)
                .build();
        return basicCardResponseDto(basicCard, buttons);
    }

    public static SkillResponseDto basicCardResponseDto(BasicCardDto basicCard,
                                                        ButtonDto... buttons) {
        for(ButtonDto button : buttons) {
            basicCard.getButtons().add(button);
        }
        ComponentDto componentDto = ComponentDto.builder().basicCard(basicCard).build();
        SkillTemplateDto skillTemplateDto = SkillTemplateDto.builder().build();
        skillTemplateDto.getOutputs().add(componentDto);
        return SkillResponseDto.builder().template(skillTemplateDto).build();
    }

    public static SkillResponseDto basicCardResponseDto(List<BasicCardDto> basicCards,
                                                        ButtonDto... buttons) {
        if (basicCards.size() == 0 || basicCards.size() > 3) {
            throw new InvalidValueException("컴포넌트의 크기는 1 이상 3 이하이여야 합니다.");
        }

        for (ButtonDto button : buttons) {
            basicCards.get(basicCards.size() - 1).getButtons().add(button);
        }

        SkillTemplateDto skillTemplateDto = SkillTemplateDto.builder().build();
        for (BasicCardDto basicCard : basicCards) {
            ComponentDto componentDto = ComponentDto.builder().basicCard(basicCard).build();
            skillTemplateDto.getOutputs().add(componentDto);
        }

        return SkillResponseDto.builder().template(skillTemplateDto).build();
    }

    public static SkillResponseDto basicCardCarouselResponseDto(List<BasicCardDto> basicCards) {
        CarouselDto carousel = CarouselDto.builder()
                .type("basicCard")
                .items(basicCards)
                .build();

        ComponentDto componentDto = ComponentDto.builder().carousel(carousel).build();
        SkillTemplateDto skillTemplateDto = SkillTemplateDto.builder().build();
        skillTemplateDto.getOutputs().add(componentDto);
        return SkillResponseDto.builder().template(skillTemplateDto).build();
    }

    public static SkillResponseDto simpleTextWithQuickReplies(@NonNull String text,
                                                              String... messages) {
        // message == label
        QuickReplyDto[] quickReplies = Arrays.stream(messages).map(message ->
                QuickReplyDto.builder()
                        .label(message)
                        .action("message")
                        .messageText(message)
                        .build())
                .toArray(QuickReplyDto[]::new);

        return simpleTextWithQuickReplies(text, quickReplies);
    }

    public static SkillResponseDto simpleTextWithQuickReplies(@NonNull String text,
                                                              QuickReplyDto... quickReplies) {
        // message == label
        SimpleTextDto simpleText = SimpleTextDto.builder().text(text).build();
        ComponentDto component = ComponentDto.builder().simpleText(simpleText).build();
        SkillTemplateDto skillTemplate = SkillTemplateDto.builder().build();
        skillTemplate.getOutputs().add(component);
        for(QuickReplyDto reply : quickReplies) {
            skillTemplate.getQuickReplies().add(reply);
        }

        return SkillResponseDto.builder().template(skillTemplate).build();
    }
}
