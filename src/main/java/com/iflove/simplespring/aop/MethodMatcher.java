package com.iflove.simplespring.aop;

import java.lang.reflect.Method;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 * Part of a {@link Pointcut}: Checks whether the target method is eligible for advice.
 */

public interface MethodMatcher {

    /**
     * Perform static checking whether the given method matches. If this
     * @return whether or not this method matches statically
     */
    boolean matches(Method method, Class<?> targetClass);

}
