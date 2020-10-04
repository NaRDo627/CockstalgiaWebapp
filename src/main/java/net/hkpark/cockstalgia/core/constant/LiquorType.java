package net.hkpark.cockstalgia.core.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import net.hkpark.cockstalgia.core.exception.InvalidValueException;

import java.util.Arrays;

public enum LiquorType {
    WHISKEY,
    RUM,
    VODKA,
    GIN;

    @JsonCreator
    public static LiquorType of(String baseName) {
        return Arrays.stream(values())
                .filter(m -> m.name().equals(baseName))
                .findFirst()
                .orElseThrow(InvalidValueException::new);
    }
}
