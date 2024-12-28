package test.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class WorldServiceInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("do something before the Earth explodes");
        Object res = methodInvocation.proceed();
        System.out.println("do something after the Earth explodes");
        return res;
    }
}
