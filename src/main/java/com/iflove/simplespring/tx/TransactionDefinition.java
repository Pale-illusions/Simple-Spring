package com.iflove.simplespring.tx;

import java.sql.Connection;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 该接口允许开发者定制事务的各种属性，如隔离级别、传播行为、超时时间以及是否只读。
 */

public interface TransactionDefinition {
    /**
     * 这个是spring默认的事务传播行为
     * 表示当前方法必须运行在事务中。如果当前事务存在，方法将会在该事务中运行。否则，会启动一个新的事务。
     * REQUIRED 表示需要，即 B事务无论如何都要有事务。
     */
    int PROPAGATION_REQUIRED = 0;

    /**
     * 表示当前方法不需要事务上下文。但是如果存在当前事务的话，那么该方法会在这个事务中运行
     */
    int PROPAGATION_SUPPORTS = 1;

    /**
     * (强制性的) 表示该方法必须在事务中运行。如果当前事务不存在，则会抛出一个异常。
     */
    int PROPAGATION_MANDATORY = 2;

    /**
     * (需要一个新事务) 表示当前方法必须运行在它自己的事务中。一个新的事务将被启动。
     * 如果存在当前事务，在该方法执行期间，当前事务会被挂起。
     */
    int PROPAGATION_REQUIRES_NEW = 3;

    /**
     * (不支持事务) 表示该方法不应该运行在事务中。
     * 如果存在当前事务，在该方法运行期间，当前事务将被挂起。
     */
    int PROPAGATION_NOT_SUPPORTED = 4;

    /**
     * (不会运行在有事务的环境) 表示当前方法不应该运行在事务上下文中，如果当前有一个事务正在运行，则会抛出异常
     */
    int PROPAGATION_NEVER = 5;

    /**
     * (嵌套的) 表示如果当前已经存在一个事务，那么该方法将会在嵌套事务中运行。（MySQL 采用 SAVEPOINT 保护点实现的）
     * 嵌套的事务可以独立于当前事务进行独立地提交或回滚，如果当前事务不存在，那么其行为与 REQUIRED 一样。
     * 注意不同数据库对这种传播行为的支持是有所差异的。可以参考资源管理器的文档来确认它们是否支持嵌套事务。
     */
    int PROPAGATION_NESTED = 6;

    /**
     * 使用默认的隔离级别
     */
    int ISOLATION_DEFAULT = -1;

    /**
     * 读未提交
     */
    int ISOLATION_READ_UNCOMMITTED = Connection.TRANSACTION_READ_UNCOMMITTED;

    /**
     * 读已提交
     */
    int ISOLATION_READ_COMMITTED = Connection.TRANSACTION_READ_COMMITTED;

    /**
     * 可重复读
     */
    int ISOLATION_REPEATABLE_READ = Connection.TRANSACTION_REPEATABLE_READ;

    /**
     * 串行化
     */
    int ISOLATION_SERIALIZABLE = Connection.TRANSACTION_SERIALIZABLE;

    /**
     * 默认超时时间
     */
    int TIMEOUT_DEFAULT = -1;

    /**
     * 获取传播行为
     */
    int getPropagationBehavior();

    /**
     * 获取事务隔离级别
     */
    int getIsolationLevel();

    /**
     * 获取事务超时时间
     */
    int getTimeout();

    boolean isReadOnly();

    String getName();
}
