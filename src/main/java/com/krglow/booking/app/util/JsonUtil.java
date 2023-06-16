package com.krglow.booking.app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtil {

    public static <T> T deserialize(String s, Class<T> clazz, ObjectMapper mapper) {
        T res = null;
        try {
            if (s != null && !s.isBlank()) {
                res = mapper.readValue(s, clazz);
            }
        } catch (JsonProcessingException ex) {
            log.error(ex.getLocalizedMessage(), ex);
        }
        return res;
    }

    public static <T> String serialize(T object, ObjectMapper mapper) {
        String res = null;
        try {
            res = mapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            log.error(ex.getLocalizedMessage(), ex);
        }
        return res;
    }
}
