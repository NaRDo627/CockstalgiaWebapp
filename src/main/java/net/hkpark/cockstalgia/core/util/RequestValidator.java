package net.hkpark.cockstalgia.core.util;

import net.hkpark.cockstalgia.core.exception.InvalidValueException;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class RequestValidator {
    private RequestValidator() { }

    public static void requireNonNull(Object obj, String msg) {
        msg = StringUtils.isEmpty(msg) ? "요청이 잘못되었습니다." : msg;
        if (Objects.isNull(obj)) {
            throw new InvalidValueException(msg);
        }
    }

    public static void requireNonNull(Object obj) {
        requireNonNull(obj, null);
    }
}
