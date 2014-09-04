package com.scaleset.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

public class Coerce {

    private static Coerce instance = new Coerce();
    private ObjectMapper mapper = new ObjectMapper();

    public Coerce() {
    }

    public Coerce(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public static Coerce getInstance() {
        return instance;
    }

    public static <T> T to(Object value, Class<T> type) {
        return instance.convert(value, type);
    }

    public static <T> T to(Object value, Class<T> type, T fallback) {
        return instance.convert(value, type, fallback);
    }

    public static Boolean toBoolean(Object value, Boolean fallback) {
        return instance.convert(value, Boolean.class, fallback);
    }

    public static Boolean toBoolean(Object value) {
        return instance.convert(value, Boolean.class);
    }

    public static Date toDate(Object value) {
        return instance.convert(value, Date.class);
    }

    public static Date toDate(Object value, Date fallback) {
        return instance.convert(value, Date.class, fallback);
    }

    public static Double toDouble(Object value, Number fallback) {
        return instance.convert(value, Double.class, fallback == null ? null : fallback.doubleValue());
    }

    public static Double toDouble(Object value) {
        return instance.convert(value, Double.class);
    }

    public static Integer toInteger(Object value, Number fallback) {
        return instance.convert(value, Integer.class, fallback == null ? null : fallback.intValue());
    }

    public static Integer toInteger(Object value) {
        return instance.convert(value, Integer.class);
    }

    public static Long toLong(Object value) {
        return instance.convert(value, Long.class);
    }

    public static Long toLong(Object value, Number fallback) {
        return instance.convert(value, Long.class, fallback == null ? null : fallback.longValue());
    }

    public static String toString(Object value) {
        return instance.convert(value, String.class);
    }

    public static String toString(Object value, String fallback) {
        return instance.convert(value, String.class, fallback);
    }

    public <T> T convert(Object value, Class<T> type, T fallback) {
        T result = null;
        try {
            result = mapper.convertValue(value, type);
        } catch (Exception e) {
        }
        if (result == null) {
            result = fallback;
        }
        return result;
    }

    public <T> T convert(Object value, Class<T> type) {
        return convert(value, type, null);
    }

    ObjectMapper getObjectMapper() {
        return mapper;
    }
}
