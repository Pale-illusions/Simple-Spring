package com.iflove.simplespring.core.convert.converter;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 类型处理转换接口
 * A converter converts a source object of type {@code S} to a target of type {@code T}.
 */

public interface Converter<S, T> {

    /** Convert the source object of type {@code S} to target type {@code T}. */
    T convert(S source);

}
