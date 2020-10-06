package net.hkpark.cockstalgia.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link net.hkpark.cockstalgia.core.util.ObjectMapper} 에 사용되는 어노테이션<br />
 * 사용된 Field 에는 자료형에 맞게 날짜 데이터가 변환된다.<br />
 * 변환 기준은 {@link java.time.format.DateTimeFormatter} 사용 <br />
 * {@link java.time.LocalDateTime} 객체의 필드에 사용할 경우<br />
 * {@link java.lang.String} 으로 들어온 날짜를 {@link java.time.LocalDateTime}으로 변환 - 이외 상황 에러<br /><br />
 *
 * {@link java.lang.String} 객체의 필드에 사용할 경우<br />
 * {@link java.time.LocalDateTime} 으로 들어온 날짜를 {@link java.lang.String} 으로 변환 - 이외 상황 에러<br /><br />
 *
 * 그 외의 상황 : 경고 후 무시<br />
 * Caution : Destination Field 에 사용한다.
 *
 * @see java.time.format.DateTimeFormatter
 * @see java.time.LocalDateTime
 * @see net.hkpark.cockstalgia.core.util.ObjectMapper
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MappingDateFormat {
    String value() default "yyyy-MM-dd HH:mm:ss";
}
