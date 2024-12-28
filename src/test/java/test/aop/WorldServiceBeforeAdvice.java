package test.aop;

import com.iflove.simplespring.aop.BeforeAdvice;
import com.iflove.simplespring.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class WorldServiceBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("BeforeAdvice: do something before the earth explodes");
    }
}
