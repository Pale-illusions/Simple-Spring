package com.iflove.simplespring.tx.interceptor;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.thread.threadlocal.NamedThreadLocal;
import com.iflove.simplespring.beans.BeansException;
import com.iflove.simplespring.beans.factory.BeanFactory;
import com.iflove.simplespring.beans.factory.BeanFactoryAware;
import com.iflove.simplespring.beans.factory.InitializingBean;
import com.iflove.simplespring.tx.PlatformTransactionManager;
import com.iflove.simplespring.tx.TransactionStatus;
import com.iflove.simplespring.utils.ClassUtils;

import java.lang.reflect.Method;

public abstract class TransactionAspectSupport implements BeanFactoryAware, InitializingBean {

    private static final ThreadLocal<TransactionInfo> transactionInfoHolder =
            new NamedThreadLocal<>("Current aspect-driven transaction");

    private BeanFactory beanFactory;

    private TransactionAttributeSource transactionAttributeSource;

    private PlatformTransactionManager transactionManager;


    protected Object invokeWithinTransaction(Method method, Class<?> targetClass,
                                             InvocationCallback invocation)throws Throwable {
        TransactionAttributeSource tas = getTransactionAttributeSource();
        TransactionAttribute txAttr = (tas != null ? tas.getTransactionAttribute(method, targetClass) : null);

        PlatformTransactionManager tm = determineTransactionManager();
        String joinpointIdentification = methodIdentification(method, targetClass);
        TransactionInfo txInfo = createTransactionIfNecessary(tm, txAttr, joinpointIdentification);
        Object retVal=null;

        try {
            retVal=invocation.proceedWithInvocation();
        } catch (Throwable e) {
            completeTransactionAfterThrowing(txInfo, e);
            throw e;
        }finally {
            cleanupTransactionInfo(txInfo);
        }
        commitTransactionAfterReturning(txInfo);

        return retVal;
    }

    public TransactionAttributeSource getTransactionAttributeSource() {
        return transactionAttributeSource;
    }

    public void setTransactionAttributeSource(TransactionAttributeSource transactionAttributeSource) {
        this.transactionAttributeSource = transactionAttributeSource;
    }

    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * 当前使用DataSourceTransactionManager
     */
    protected PlatformTransactionManager determineTransactionManager() {
        return getTransactionManager();
    }

    /**
     * 获取目标方法的唯一标识
     */
    private String methodIdentification(Method method, Class<?> targetClass) {
        return ClassUtils.getQualifiedMethodName(method, targetClass);
    }

    protected TransactionInfo createTransactionIfNecessary(PlatformTransactionManager tm,TransactionAttribute txAttr,
                                                           String joinpointIdentification){
        if (txAttr != null && txAttr.getName() == null) {
            txAttr = new DelegatingTransactionAttribute(txAttr) {
                @Override
                public String getName() {
                    return joinpointIdentification;
                }
            };
        }

        TransactionStatus status = null;
        if (txAttr != null) {
            if (tm != null) {
                status = tm.getTransaction(txAttr);
            }
        }
        return prepareTransactionInfo(tm, txAttr, joinpointIdentification, status);
    }

    protected TransactionInfo prepareTransactionInfo(PlatformTransactionManager tm, TransactionAttribute txAttr, String joinpointIdentification, TransactionStatus status) {
        TransactionInfo txInfo = new TransactionInfo(tm, txAttr, joinpointIdentification);
        if (txAttr != null) {
            txInfo.newTransactionStatus(status);
        }
        txInfo.bindToThread();
        return txInfo;
    }

    protected void completeTransactionAfterThrowing(TransactionInfo txInfo, Throwable ex) {
        if (null != txInfo && null != txInfo.getTransactionStatus()) {

            if (txInfo.transactionAttribute != null && txInfo.transactionAttribute.rollbackOn(ex)) {
                try {
                    txInfo.getTransactionManager().rollback(txInfo.getTransactionStatus());
                } catch (RuntimeException | Error ex2) {
                    throw ex2;
                }
            } else {
                try {
                    txInfo.getTransactionManager().commit(txInfo.getTransactionStatus());
                } catch (Exception ex2) {
                    throw ex2;
                }
            }
        }
    }

    protected void cleanupTransactionInfo(TransactionInfo txInfo) {
        if (null != txInfo) {
            txInfo.restoreThreadLocalStatus();
        }
    }

    protected void commitTransactionAfterReturning(TransactionInfo txInfo) {
        if (null != txInfo && null != txInfo.getTransactionStatus()) {
            txInfo.getTransactionManager().commit(txInfo.getTransactionStatus());
        }
    }



    protected interface InvocationCallback {
        Object proceedWithInvocation() throws Throwable;
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    protected final class TransactionInfo {

        private final PlatformTransactionManager transactionManager;
        private final TransactionAttribute transactionAttribute;
        private final String joinpointIdentification;
        private TransactionStatus transactionStatus;
        private TransactionInfo oldTransactionInfo;

        public TransactionInfo(PlatformTransactionManager transactionManager,
                               TransactionAttribute transactionAttribute, String joinpointIdentification) {
            this.transactionManager = transactionManager;
            this.transactionAttribute = transactionAttribute;
            this.joinpointIdentification = joinpointIdentification;
        }

        public PlatformTransactionManager getTransactionManager() {
            Assert.state(this.transactionManager != null, "No PlatformTransactionManager set");
            return transactionManager;
        }

        public String getJoinpointIdentification() {
            return joinpointIdentification;
        }

        public TransactionAttribute getTransactionAttribute() {
            return transactionAttribute;
        }

        public void newTransactionStatus(TransactionStatus status) {
            this.transactionStatus = status;
        }

        public TransactionStatus getTransactionStatus() {
            return transactionStatus;
        }

        public boolean hasTransaction() {
            return null != this.transactionStatus;
        }
        private void bindToThread() {
            this.oldTransactionInfo = transactionInfoHolder.get();
            transactionInfoHolder.set(this);
        }

        private void restoreThreadLocalStatus() {
            transactionInfoHolder.set(this.oldTransactionInfo);
        }

    }
}
