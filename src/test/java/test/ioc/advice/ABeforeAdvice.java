package test.ioc.advice;

import com.iflove.simplespring.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class ABeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {

    }
}
