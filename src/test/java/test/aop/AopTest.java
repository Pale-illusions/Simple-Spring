package test.aop;

import com.iflove.simplespring.aop.AdvisedSupport;
import com.iflove.simplespring.aop.ClassFilter;
import com.iflove.simplespring.aop.MethodMatcher;
import com.iflove.simplespring.aop.TargetSource;
import com.iflove.simplespring.aop.aspectj.AspectJExpressionPointcut;
import com.iflove.simplespring.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.iflove.simplespring.aop.framework.CglibAopProxy;
import com.iflove.simplespring.aop.framework.JdkDynamicAopProxy;
import com.iflove.simplespring.aop.framework.ProxyFactory;
import com.iflove.simplespring.aop.framework.adapter.AfterReturningAdviceInterceptor;
import com.iflove.simplespring.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.junit.Before;
import org.junit.Test;
import test.aop.bean.WorldService;
import test.aop.bean.WorldServiceImpl;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class AopTest {

    private AdvisedSupport advisedSupport;

    @Before
    public void setup() {
        WorldService worldService = new WorldServiceImpl();
        advisedSupport = new ProxyFactory();
        //Advisor是Pointcut和Advice的组合
        String expression = "execution(* test.aop.bean.WorldService.explode(..))";
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(expression);
        AfterReturningAdviceInterceptor methodInterceptor = new AfterReturningAdviceInterceptor(new WorldServiceAfterReturnAdvice());
        advisor.setAdvice(methodInterceptor);
        TargetSource targetSource = new TargetSource(worldService);
        advisedSupport.setTargetSource(targetSource);
        advisedSupport.addAdvisor(advisor);
    }


    @Test
    public void testJdkDynamicProxy() throws Exception {
        WorldService proxy = (WorldService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        proxy.explode();
    }

    @Test
    public void testCglibDynamicProxy1() throws Exception {
        WorldService proxy = (WorldService) new CglibAopProxy(advisedSupport).getProxy();
        proxy.explode();
    }

    @Test
    public void testCglibDynamicProxy2() throws Exception {
        WorldServiceImpl proxy = (WorldServiceImpl) new CglibAopProxy(advisedSupport).getProxy();
        proxy.explode();
    }

    @Test
    public void testProxyFactory() {
        // 使用JDK动态代理
        ProxyFactory factory = (ProxyFactory) advisedSupport;
        factory.setProxyTargetClass(false);
        WorldService proxy = (WorldService) factory.getProxy();
        proxy.explode();

        // 使用CGLIB动态代理
        factory.setProxyTargetClass(true);
        proxy = (WorldService) factory.getProxy();
        proxy.explode();
    }

    @Test
    public void testBeforeAdvice() throws Exception {
        //设置BeforeAdvice
        String expression = "execution(* test.aop.bean.WorldService.explode(..))";
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(expression);
        MethodBeforeAdviceInterceptor methodInterceptor = new MethodBeforeAdviceInterceptor(new WorldServiceBeforeAdvice());
        advisor.setAdvice(methodInterceptor);
        advisedSupport.addAdvisor(advisor);
        ProxyFactory factory = (ProxyFactory) advisedSupport;
        WorldService proxy = (WorldService) factory.getProxy();
        proxy.explode();
    }

    @Test
    public void testAdvisor() throws Exception {
        WorldService worldService = new WorldServiceImpl();

        //Advisor是Pointcut和Advice的组合
        String expression = "execution(* org.springframework.test.service.WorldService.explode(..))";
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(expression);
        MethodBeforeAdviceInterceptor methodInterceptor = new MethodBeforeAdviceInterceptor(new WorldServiceBeforeAdvice());
        advisor.setAdvice(methodInterceptor);

        ClassFilter classFilter = advisor.getPointcut().getClassFilter();
        if (classFilter.matches(worldService.getClass())) {
            ProxyFactory proxyFactory = new ProxyFactory();

            TargetSource targetSource = new TargetSource(worldService);
            proxyFactory.setTargetSource(targetSource);
            proxyFactory.addAdvisor(advisor);
//			proxyFactory.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
//			advisedSupport.setProxyTargetClass(true);   //JDK or CGLIB

            WorldService proxy = (WorldService) proxyFactory.getProxy();
            proxy.explode();
        }
    }

}
