package com.iflove.simplespring.aop.framework;

import com.iflove.simplespring.aop.AdvisedSupport;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 * Factory for AOP proxies for programmatic use, rather than via a bean
 * factory. This class provides a simple way of obtaining and configuring
 * AOP proxies in code.
 */

public class ProxyFactory extends AdvisedSupport {

    public ProxyFactory() {
    }

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    private AopProxy createAopProxy() {
        if (this.isProxyTargetClass() || this.getTargetSource().getTargetClass().length == 0) {
            return new CglibAopProxy(this);
        }

        return new JdkDynamicAopProxy(this);
    }
}
