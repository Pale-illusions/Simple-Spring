package com.iflove.simplespring.aop.framework;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote AOP代理的抽象
 * Delegate interface for a configured AOP proxy, allowing for the creation
 * of actual proxy objects.
 *
 * <p>Out-of-the-box implementations are available for JDK dynamic proxies
 * and for CGLIB proxies, as applied by DefaultAopProxyFactory
 */

public interface AopProxy {

    Object getProxy();
}
