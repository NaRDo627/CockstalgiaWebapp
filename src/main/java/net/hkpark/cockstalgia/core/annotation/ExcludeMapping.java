package net.hkpark.cockstalgia.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link net.hkpark.cockstalgia.core.util.ObjectMapper} 에 사용되는 어노테이션<br />
 * 사용된 필드는 매핑시 대상에서 제외된다. <br />
 * (Source 로 사용되었을 경우에만 해당, Destination 일 경우 적용되지 않는다.)<br />
 *
 * @see net.hkpark.cockstalgia.core.util.ObjectMapper
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcludeMapping {
}
