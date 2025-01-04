package com.iflove.simplespring.aop.support;

import com.iflove.simplespring.aop.ClassFilter;
import com.iflove.simplespring.aop.MethodMatcher;
import com.iflove.simplespring.aop.Pointcut;

public abstract class StaticMethodMatcherPointcut extends StaticMethodMatcher implements Pointcut {

    private ClassFilter classFilter = ClassFilter.TRUE;

    public void setClassFilter(ClassFilter classFilter) {
        this.classFilter = classFilter;
    }

    public ClassFilter getClassFilter() {
        return classFilter;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
