package com.iflove.simplespring.core.convert.support;

import com.iflove.simplespring.core.convert.converter.ConverterRegistry;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 * A specialization of {@link GenericConversionService} configured by default
 * with converters appropriate for most environments.
 */

public class DefaultConversionService extends GenericConversionService{

    public DefaultConversionService() {
        addDefaultConverters(this);
    }

    public static void addDefaultConverters(ConverterRegistry converterRegistry) {
        // 添加各类类型转换工厂
        converterRegistry.addConverterFactory(new StringToNumberConverterFactory());
    }

}

