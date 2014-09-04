package com.scaleset.utils;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.scaleset.utils.Coerce;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Extensible {

    private Map<String, Object> any = new HashMap<String, Object>();

    public Extensible() {
    }

    @JsonAnyGetter
    public Map<String, Object> anyGetter() {
        return any;
    }

    public Object get(String key) {
        return this.any.get(key);
    }

    public Object get(String key, Object fallback) {
        Object result = fallback;
        if (any.containsKey(key)) {
            result = any.get(key);
        }
        return result;
    }

    public Boolean getBoolean(String key, Boolean fallback) {
        return Coerce.toBoolean(key, fallback);
    }

    public Double getDouble(String key, Number fallback) {
        return Coerce.toDouble(any.get(key), fallback);
    }

    public Integer getInteger(String key, Number fallback) {
        return Coerce.toInteger(any.get(key), fallback);
    }

    public String getString(String key, String fallback) {
        return Coerce.toString(any.get(key), fallback);
    }

    public String getString(String key) {
        return Coerce.toString(any.get(key));
    }

    @JsonAnySetter
    public Object put(String key, Object value) {
        return this.any.put(key, value);
    }

    public void putAll(Map<String, Object> other) {
        any.putAll(other);
    }

    public void replaceEmptyStringsWithNull() {
        replaceEmptyStringsWithNull(any);
    }

    public static void replaceEmptyStringsWithNull(Map<String, Object> map) {
        for (String key : map.keySet()) {
            Object value = map.get(key);
            if ("".equals(value)) {
                map.put(key, null);
            }
        }
    }

}
