package net.hkpark.cockstalgia.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link net.hkpark.cockstalgia.core.util.ObjectMapper} 에 사용되는 어노테이션<br />
 * 사용된 필드의 매핑시 이름을 정의한다.<br />
 * Source 와 Destination 일 경우 모두 해당<br />
 *
 * @see net.hkpark.cockstalgia.core.util.ObjectMapper
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MappingName {
    String value();
}
