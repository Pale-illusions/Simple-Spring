package test.aop;

import com.iflove.simplespring.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

public class WorldServiceAfterReturnAdvice implements AfterReturningAdvice {

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		System.out.println("AfterAdvice: do something after the earth explodes");
	}
}
