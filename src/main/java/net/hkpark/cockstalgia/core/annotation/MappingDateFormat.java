package net.hkpark.cockstalgia.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ${@link net.hkpark.cockstalgia.core.util.ObjectMapper} 에 사용되는 어노테이션
 * 사용된 필드에는 자료형에 맞게 날짜 데이터가 변환된다.
 * 변환 기준은 {@link java.time.format.DateTimeFormatter} 사용
 * LocalDateTime 객체의 필드에 사용할 경우 : String 으로 들어온 날짜를 LocalDateTime으로 변환 - 이외 상황 에러
 * String 객체의 필드에 사용할 경우 : LocalDateTime으로 들어온 날짜를 String으로 변환 - 이외 상황 에러
 * 그 외의 상황 : 경고 후 무시
 *
 * @see java.time.format.DateTimeFormatter
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MappingDateFormat {
    String value() default "yyyy-MM-dd HH:mm:ss";
}
