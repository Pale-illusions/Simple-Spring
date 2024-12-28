package com.iflove.simplespring.beans.factory;

import com.iflove.simplespring.beans.BeansException;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 * Defines a factory which can return an Object instance
 * (possibly shared or independent) when invoked.
 */

public interface ObjectFactory<T> {

    T getObject() throws BeansException;
}
