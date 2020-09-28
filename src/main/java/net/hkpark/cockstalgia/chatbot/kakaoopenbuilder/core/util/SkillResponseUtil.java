package net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.util;

import lombok.NonNull;
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
        Arrays.stream(buttons).map(basicCard.getButtons()::add).close();
        ComponentDto componentDto = ComponentDto.builder().basicCard(basicCard).build();
        SkillTemplateDto skillTemplateDto = SkillTemplateDto.builder().build();
        skillTemplateDto.getOutputs().add(componentDto);
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
}
