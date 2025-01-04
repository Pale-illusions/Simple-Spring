package com.iflove.simplespring.tx;

import java.io.Flushable;
import java.io.IOException;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 * 该接口用于记录事务执行过程中的状态。它包含了一些关键信息，例如是否处于活动状态、是否可以提交、是否需要回滚，挂起的资源等。
 * 通过检查事务状态，事务管理器可以决定是否继续执行事务操作。
 */

public interface TransactionStatus extends SavepointManager, Flushable {
    /**
     * 是否开启新的事务
     */
    boolean isNewTransaction();

    boolean hasSavepoint();

    void setRollbackOnly();

    boolean isRollbackOnly();

    @Override
    void flush() throws IOException;

    boolean isCompleted();
}
