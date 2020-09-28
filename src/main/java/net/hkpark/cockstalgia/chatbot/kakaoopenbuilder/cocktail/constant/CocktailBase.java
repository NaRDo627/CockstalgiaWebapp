package net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.cocktail.constant;

import net.hkpark.cockstalgia.core.exception.InvalidValueException;

import java.util.Arrays;

public enum CocktailBase {
    WHISKEY,
    RUM,
    VODKA,
    GIN;

    public static CocktailBase of(String baseName) {
        return Arrays.stream(values())
                .filter(m -> m.name().equals(baseName))
                .findFirst()
                .orElseThrow(InvalidValueException::new);
    }
}
