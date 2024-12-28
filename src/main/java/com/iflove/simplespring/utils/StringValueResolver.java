package com.iflove.simplespring.utils;

import com.iflove.simplespring.beans.factory.config.ConfigurableBeanFactory;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 * Simple strategy interface for resolving a String value.
 * Used by {@link ConfigurableBeanFactory}.
 */

public interface StringValueResolver {

    String resolveStringValue(String strVal);

}
