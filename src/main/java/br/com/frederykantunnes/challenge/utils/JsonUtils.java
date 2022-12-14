package br.com.frederykantunnes.challenge.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {

    private static ObjectMapper mObjectMapper;

    private static ObjectMapper getMapper() {
        if (mObjectMapper == null) {
            mObjectMapper = new ObjectMapper();
        }
        return mObjectMapper;
    }


    public static String toJson(Object object) {
        try {
            return getMapper().writeValueAsString(object);
        }catch (IOException exception){
            return "Json Parse Error";
        }
    }

}
