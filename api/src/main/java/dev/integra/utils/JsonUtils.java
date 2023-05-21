package dev.integra.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.Map;

/**
 * @author milizm
 * @since 1.0.0
 */
public class JsonUtils {

    private static final Gson gson = new Gson();

    public static Map<String, Object> toMap(String json) {
        return gson.fromJson(json, Map.class);
    }

    public static String toJson(Map<String, Object> map) {
        return gson.toJson(map);
    }

    public static JsonElement toJsonElement(Object obj) {
        return gson.toJsonTree(obj);
    }
}
