package jfang.games.baohuang.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author jfang
 */
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static String writeToString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static <T> T parse(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, clazz);
    }

    public static <T> T parse(String json, TypeReference<T> typeReference) throws IOException {
        return objectMapper.readValue(json, typeReference);
    }

    public static <T> T parse(InputStream inputStream, Class<T> clazz) throws IOException {
        return objectMapper.readValue(inputStream, clazz);
    }

    public static <T> T parse(InputStream inputStream, TypeReference<T> typeReference) throws IOException {
        return objectMapper.readValue(inputStream, typeReference);
    }

    public static JsonNode readTree(String json) throws IOException {
        return objectMapper.readTree(json);
    }

    public static <T> T convertValue(Object data, Class<T> clazz) {
        return objectMapper.convertValue(data, clazz);
    }
}
