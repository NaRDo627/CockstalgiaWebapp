package net.hkpark.cockstalgia.core.security;

import lombok.Getter;
import net.hkpark.cockstalgia.core.exception.InvalidValueException;

import java.util.Arrays;

public enum SocialType {
    FACEBOOK("facebook"),
    GOOGLE("google"),
    KAKAO("kakao"),
    NAVER("naver");

    private static final String ROLE_PREFIX = "ROLE_";

    @Getter
    private final String name;

    SocialType(String name) { this.name = name; }

    public static SocialType of(String name) {
        name = name.toLowerCase();
        String finalName = name;
        return Arrays.stream(values())
                .filter(m -> m.getName().equals(finalName))
                .findFirst()
                .orElseThrow(InvalidValueException::new);
    }

    public String getRoleType() { return ROLE_PREFIX + name.toUpperCase(); }

    public boolean isEquals(String authority) { return this.getRoleType().equals(authority);}
}
