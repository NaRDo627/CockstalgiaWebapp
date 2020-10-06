package net.hkpark.cockstalgia.core.util;

import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.core.annotation.MappingDateFormat;
import net.hkpark.cockstalgia.core.annotation.ExcludeMapping;
import net.hkpark.cockstalgia.core.annotation.MappingName;
import net.hkpark.cockstalgia.core.exception.BusinessException;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class ObjectMapper {
    private ObjectMapper() { }

    public static <T> T mapObject(Object object, Class<T> targetClass) {
        Field[] fields = object.getClass().getDeclaredFields();
        Map<String, Object> fieldMap = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getAnnotation(ExcludeMapping.class) != null) {
                continue;
            }

            try {
                if(Objects.isNull(field.get(object))) {
                    continue;
                }

                String fieldName = field.getName();
                if (field.getAnnotation(MappingName.class) != null) {
                    MappingName mappingName = field.getAnnotation(MappingName.class);
                    fieldName = mappingName.value();
                }
                fieldMap.put(fieldName, field.get(object));
            } catch (IllegalAccessException e) {
                log.error("Access to {} has failed", field.getName());
            }
        }

        try {
            Field[] targetFields = targetClass.getDeclaredFields();
            T target = targetClass.getDeclaredConstructor().newInstance();

            for (Field targetField : targetFields) {
                targetField.setAccessible(true);
                String fieldName = targetField.getName();
                if (targetField.getAnnotation(MappingName.class) != null) {
                    MappingName mappingName = targetField.getAnnotation(MappingName.class);
                    fieldName = mappingName.value();
                }
                if (! fieldMap.containsKey(fieldName)) {
                    continue;
                }

                Object incomingValue = fieldMap.get(targetField.getName());

                /*if (targetField.isEnumConstant()) {
                    Enum<?> enumValue = (Enum<?>)incomingValue;
                    for(Enum<?> e : enumValue.getClass().getEnumConstants()) {
                        if (e != incomingValue) {
                            continue;
                        }

                        targetField.set(target, e);
                    }
                } else {
                    targetField.set(target, incomingValue);
                }*/


                if (targetField.getAnnotation(MappingDateFormat.class) != null) {
                    if (! (targetField.getType().equals(LocalDateTime.class)) && ! (targetField.getType().equals(String.class))) {
                        log.warn("Field used MappingDateFormat annotation is not LocalDateTime or String. ignoring..");
                        continue;
                    }

                    MappingDateFormat mappingDateFormat = targetField.getAnnotation(MappingDateFormat.class);
                    String formatString = mappingDateFormat.value();

                    if (incomingValue instanceof LocalDateTime) {
                        if (! targetField.getType().equals(String.class)) {
                            throw new BusinessException("Target field is String but incoming field is not LocalDateTime");
                        }

                        LocalDateTime incomingDatetime = (LocalDateTime) incomingValue;
                        targetField.set(target,
                                incomingDatetime.format(DateTimeFormatter.ofPattern(formatString)));

                    } else if (incomingValue instanceof String){
                        if (! targetField.getType().equals(LocalDateTime.class)) {
                            throw new BusinessException("Target field is LocalDateTime but incoming field is not String");
                        }

                        String incomingDatetimeString = (String) incomingValue;
                        targetField.set(target,
                                LocalDateTime.parse(incomingDatetimeString, DateTimeFormatter.ofPattern(formatString)));
                    }
                    continue;
                }

                targetField.set(target, incomingValue);
            }

            return target;
        } catch (Exception e) {
            log.error("Mapping failed : ", e);
            throw new BusinessException(e);
        }
    }
}
