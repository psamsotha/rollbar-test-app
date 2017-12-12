package com.demo.provider;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;


@Provider
public class FlagParamConverterProvider implements ParamConverterProvider {

    @Override
    public <T> ParamConverter<T> getConverter(
            Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType != Flag.class) {
            return null;
        }

        return new ParamConverter<T>() {

            @Override
            public T fromString(String value) {
                return (T)new Flag(value);
            }

            @Override
            public String toString(T value) { return null; }
        };
    }
}