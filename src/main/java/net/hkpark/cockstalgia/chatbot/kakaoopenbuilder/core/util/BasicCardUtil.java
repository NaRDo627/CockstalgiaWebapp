package net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.util;

import lombok.NonNull;
import net.hkpark.kakao.openbuilder.dto.response.*;
import org.springframework.lang.Nullable;

import java.util.Arrays;

public class BasicCardUtil {
    private BasicCardUtil() { }

    public static BasicCardDto getOne(@NonNull String title,
                                      @NonNull String description,
                                      @Nullable String thumbnailUrl,
                                      ButtonDto... buttons) {
        return getOne(title, description, thumbnailUrl, false, 0, 0, buttons);
    }

    public static BasicCardDto getOne(@NonNull String title,
                                        @NonNull String description,
                                        @Nullable String thumbnailUrl,
                                         boolean thumbnailFixedRatio,
                                         int width,
                                         int height,
                                         ButtonDto... buttons) {

        ThumbnailDto thumbnail = null;
        if (thumbnailUrl != null) {
            thumbnail = ThumbnailDto.builder()
                    .imageUrl(thumbnailUrl)
                    .fixedRatio(thumbnailFixedRatio)
                    .width(width)
                    .height(height)
                    .build();
        }

        BasicCardDto basicCard = BasicCardDto.builder()
                .title(title)
                .description(description)
                .thumbnail(thumbnail)
                .build();
        for(ButtonDto button : buttons) {
            basicCard.getButtons().add(button);
        }
        return basicCard;
    }
}
