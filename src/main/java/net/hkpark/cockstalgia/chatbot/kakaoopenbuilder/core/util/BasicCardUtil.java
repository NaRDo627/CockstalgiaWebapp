package net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.util;

import lombok.NonNull;
import net.hkpark.kakao.openbuilder.dto.response.*;

import java.util.Arrays;

public class BasicCardUtil {
    private BasicCardUtil() { }

    public static BasicCardDto getOne(@NonNull String title,
                                      @NonNull String description,
                                      @NonNull String thumbnailUrl,
                                      ButtonDto... buttons) {
        return getOne(title, description, thumbnailUrl, false, 0, 0, buttons);
    }

    public static BasicCardDto getOne(@NonNull String title,
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
        return basicCard;
    }
}
