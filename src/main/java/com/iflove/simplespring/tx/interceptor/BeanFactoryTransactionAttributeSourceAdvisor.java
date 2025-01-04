package com.iflove.simplespring.tx.interceptor;


import com.iflove.simplespring.aop.ClassFilter;
import com.iflove.simplespring.aop.Pointcut;
import com.iflove.simplespring.aop.support.AbstractBeanFactoryPointcutAdvisor;

public class BeanFactoryTransactionAttributeSourceAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    private TransactionAttributeSource transactionAttributeSource;

    private final TransactionAttributeSourcePointcut pointcut=new TransactionAttributeSourcePointcut() {
        @Override
        protected TransactionAttributeSource getTransactionAttributeSource() {
            return transactionAttributeSource;
        }
    };

    public void setTransactionAttributeSource(TransactionAttributeSource transactionAttributeSource) {
        this.transactionAttributeSource = transactionAttributeSource;
    }

    public void setClassFilter(ClassFilter classFilter){
        this.pointcut.setClassFilter(classFilter);
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }
}
