package com.iflove.simplespring.aop.support;

import com.iflove.simplespring.aop.MethodMatcher;

import java.lang.reflect.Method;

public abstract class StaticMethodMatcher implements MethodMatcher {

    @Override
    public boolean matches(Method method, Class<?> clazz) {
        throw new UnsupportedOperationException("Illegal MethodMatcher usage");
    }
}
