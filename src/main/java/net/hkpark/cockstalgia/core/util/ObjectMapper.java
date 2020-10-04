package net.hkpark.cockstalgia.core.util;

import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.core.annotation.ExcludeMapping;
import net.hkpark.cockstalgia.core.annotation.MappingName;
import net.hkpark.cockstalgia.core.exception.BusinessException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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
                String fieldName = field.getName();
                if (field.getAnnotation(MappingName.class) != null) {
                    MappingName mappingName = field.getAnnotation(MappingName.class);
                    fieldName = mappingName.value();
                }
                fieldMap.put(fieldName, field.get(object));
            } catch (IllegalAccessException e) {
                log.error("Access failed to {} has failed", field.getName());
            }
        }

        try {
            Field[] targetFields = targetClass.getDeclaredFields();
            T target = targetClass.getDeclaredConstructor().newInstance();

            for (Field field : targetFields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (field.getAnnotation(MappingName.class) != null) {
                    MappingName mappingName = field.getAnnotation(MappingName.class);
                    fieldName = mappingName.value();
                }
                if (! fieldMap.containsKey(fieldName)) {
                    continue;
                }

                Object value = fieldMap.get(field.getName());

                /*if (field.isEnumConstant()) {
                    Enum<?> enumValue = (Enum<?>)value;
                    for(Enum<?> e : enumValue.getClass().getEnumConstants()) {
                        if (e != value) {
                            continue;
                        }

                        field.set(target, e);
                    }
                } else {
                    field.set(target, value);
                }*/
                field.set(target, value);
            }

            return target;
        } catch (Exception e) {
            throw new BusinessException("Mapping failed : ", e);
        }
    }
}
