package com.iflove.simplespring.aop.aspectj;

import com.iflove.simplespring.aop.Pointcut;
import com.iflove.simplespring.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote Spring AOP Advisor that can be used for any AspectJ pointcut expression.
 */

public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {
    // 切面
    private AspectJExpressionPointcut pointcut;
    // 具体的拦截方法
    private Advice advice;
    // 表达式
    private String expression;

    public void setExpression(String expression){
        this.expression = expression;
    }

    @Override
    public Pointcut getPointcut() {
        if (null == pointcut) {
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice){
        this.advice = advice;
    }

}
