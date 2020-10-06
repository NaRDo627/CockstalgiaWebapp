package net.hkpark.cockstalgia.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link net.hkpark.cockstalgia.core.util.ObjectMapper} 에 사용되는 어노테이션<br />
 * 사용된 Field 의 매핑시 null 일 경우 매핑할지 결정한다.<br />
 * false 일 경우 - Source 가 null 일 때 매핑하지 않음(기본)<br />
 * true 일 경우 - Source 가 null 일 때 null 값으로 매핑함<br />
 * Source Field 에 사용<br />
 *
 * @see net.hkpark.cockstalgia.core.util.ObjectMapper
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MappingNull {
    boolean value() default false;
}
