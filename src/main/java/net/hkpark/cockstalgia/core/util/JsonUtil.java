package net.hkpark.cockstalgia.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class JsonUtil {
    /* 인스턴스화 방지 */
    private JsonUtil() { }

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static <T> T convertJsonStringToObjectClass(String jsonString, Class<T> classType) throws IOException {
        return MAPPER.readValue(jsonString, classType);
    }

    public static <T> T convertMapToObjectClass(Map<String, Object> jsonMap, Class<T> classType) {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return MAPPER.convertValue(jsonMap, classType);
    }

    public static String convertObjectToJsonString(Object objJson) throws JsonProcessingException {
        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(objJson);
    }
}
