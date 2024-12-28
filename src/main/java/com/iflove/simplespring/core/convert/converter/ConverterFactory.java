package com.iflove.simplespring.core.convert.converter;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 类型转换工厂
 * A factory for "ranged" converters that can convert objects from S to subtypes of R.
 */

public interface ConverterFactory<S, R> {

    /**
     * Get the converter to convert from S to target type T, where T is also an instance of R.
     * @param <T> the target type
     * @param targetType the target type to convert to
     * @return a converter from S to T
     */
    <T extends R> Converter<S, T> getConverter(Class<T> targetType);

}
