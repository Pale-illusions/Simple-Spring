package com.iflove.simplespring.aop.framework;

import com.iflove.simplespring.aop.AdvisedSupport;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public interface AdvisorChainFactory {

    List<Object> getInterceptorsAndDynamicInterceptionAdvice(AdvisedSupport config, Method method, Class<?> targetClass);

}
