package net.hkpark.cockstalgia.core.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.hkpark.cockstalgia.core.exception.InvalidValueException;

import java.util.Arrays;

@AllArgsConstructor
public enum MemberType {
    NEWBIE("신입 부원"),
    EXTENDER("활동 연장 부원"),
    OB("OB"),
    EXISTING("기존 부원");

    @Getter
    private final String koreanName;

    @JsonCreator
    public static MemberType of(String roleName) {
        return Arrays.stream(values())
                .filter(m -> m.name().equals(roleName))
                .findFirst()
                .orElseThrow(InvalidValueException::new);
    }
}
