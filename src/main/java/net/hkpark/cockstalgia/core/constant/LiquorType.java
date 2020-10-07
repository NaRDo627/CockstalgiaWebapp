package net.hkpark.cockstalgia.core.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.hkpark.cockstalgia.core.exception.InvalidValueException;

import java.util.Arrays;

@AllArgsConstructor
public enum LiquorType {
    WHISKEY("위스키"),
    RUM("럼"),
    VODKA("보드카"),
    GIN("진"),
    TEQUILA("데킬라"),
    LIQUOR("리큐르"),
    SOJU("소주"),
    BEER("맥주");

    @Getter
    private final String koreanName;

    @JsonCreator
    public static LiquorType of(String baseName) {
        return Arrays.stream(values())
                .filter(m -> m.name().equals(baseName))
                .findFirst()
                .orElseThrow(InvalidValueException::new);
    }
}
