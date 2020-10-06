package net.hkpark.cockstalgia.core.util;

import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.core.annotation.MappingDateFormat;
import net.hkpark.cockstalgia.core.annotation.ExcludeMapping;
import net.hkpark.cockstalgia.core.annotation.MappingName;
import net.hkpark.cockstalgia.core.exception.BusinessException;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 객체의 필드를 옮겨주는 Mapper Utility 클래스
 * Source 객체의 Field 를 Destination 객체의 Field 로 복사한다.
 * 매핑시 규칙은 어노테이션으로 정의한다.
 *
 * @see net.hkpark.cockstalgia.core.annotation.MappingName
 */
@Slf4j
public class ObjectMapper {
    private ObjectMapper() { }

    public static <T> T mapObject(Object sourceObject, Class<T> destinationClass) {
        Field[] sourceFields = sourceObject.getClass().getDeclaredFields();
        Map<String, Object> fieldMap = new HashMap<>();
        for (Field sourceField : sourceFields) {
            sourceField.setAccessible(true);
            if (sourceField.getAnnotation(ExcludeMapping.class) != null) {
                continue;
            }

            try {
                if(Objects.isNull(sourceField.get(sourceObject))) {
                    continue;
                }

                String fieldName = sourceField.getName();
                if (sourceField.getAnnotation(MappingName.class) != null) {
                    MappingName mappingName = sourceField.getAnnotation(MappingName.class);
                    fieldName = mappingName.value();
                }
                fieldMap.put(fieldName, sourceField.get(sourceObject));
            } catch (IllegalAccessException e) {
                log.error("Access to {} has failed", sourceField.getName());
            }
        }

        try {
            Field[] destinationFields = destinationClass.getDeclaredFields();
            T destinationObject = destinationClass.getDeclaredConstructor().newInstance();

            for (Field destinationField : destinationFields) {
                destinationField.setAccessible(true);
                String fieldName = destinationField.getName();
                if (destinationField.getAnnotation(MappingName.class) != null) {
                    MappingName mappingName = destinationField.getAnnotation(MappingName.class);
                    fieldName = mappingName.value();
                }
                if (! fieldMap.containsKey(fieldName)) {
                    continue;
                }

                Object incomingValue = fieldMap.get(destinationField.getName());

                /*if (destinationField.isEnumConstant()) {
                    Enum<?> enumValue = (Enum<?>)incomingValue;
                    for(Enum<?> e : enumValue.getClass().getEnumConstants()) {
                        if (e != incomingValue) {
                            continue;
                        }

                        destinationField.set(destinationObject, e);
                    }
                } else {
                    destinationField.set(destinationObject, incomingValue);
                }*/


                if (destinationField.getAnnotation(MappingDateFormat.class) != null) {
                    if (! (destinationField.getType().equals(LocalDateTime.class)) && ! (destinationField.getType().equals(String.class))) {
                        log.warn("Field used MappingDateFormat annotation is not LocalDateTime or String. ignoring..");
                        continue;
                    }

                    MappingDateFormat mappingDateFormat = destinationField.getAnnotation(MappingDateFormat.class);
                    String formatString = mappingDateFormat.value();

                    if (incomingValue instanceof LocalDateTime) {
                        if (! destinationField.getType().equals(String.class)) {
                            throw new BusinessException("Target field is String but incoming field is not LocalDateTime");
                        }

                        LocalDateTime incomingDatetime = (LocalDateTime) incomingValue;
                        destinationField.set(destinationObject,
                                incomingDatetime.format(DateTimeFormatter.ofPattern(formatString)));

                    } else if (incomingValue instanceof String){
                        if (! destinationField.getType().equals(LocalDateTime.class)) {
                            throw new BusinessException("Target field is LocalDateTime but incoming field is not String");
                        }

                        String incomingDatetimeString = (String) incomingValue;
                        destinationField.set(destinationObject,
                                LocalDateTime.parse(incomingDatetimeString, DateTimeFormatter.ofPattern(formatString)));
                    }
                    continue;
                }

                destinationField.set(destinationObject, incomingValue);
            }

            return destinationObject;
        } catch (Exception e) {
            log.error("Mapping failed : ", e);
            throw new BusinessException(e);
        }
    }
}
