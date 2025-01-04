package com.iflove.simplespring.tx.support;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class DefaultTransactionStatus extends AbstractTransactionStatus {
    private final Object transaction;

    private final boolean newTransaction;

    /**
     * 创建一个新的 {@code DefaultTransactionStatus} 实例 .
     * @param transaction 可以保存状态的底层事务对象, 用于内部事务实现
     * @param newTransaction 这是否是一个新的事务，如果不是则加入存在的事务
     */
    public DefaultTransactionStatus(Object transaction, boolean newTransaction) {
        this.transaction = transaction;
        this.newTransaction = newTransaction;
    }

    public Object getTransaction() {
        return transaction;
    }

    public boolean hasTransaction() {
        return this.transaction != null;
    }

    @Override
    public boolean isNewTransaction() {
        return hasTransaction() && this.newTransaction;
    }
}
