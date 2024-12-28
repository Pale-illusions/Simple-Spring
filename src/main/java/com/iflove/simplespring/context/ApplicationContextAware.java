package com.iflove.simplespring.context;

import com.iflove.simplespring.beans.BeansException;
import com.iflove.simplespring.beans.factory.Aware;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 实现此接口，既能感知到所属的 ApplicationContext
 * Interface to be implemented by any object that wishes to be notified
 * of the {@link ApplicationContext} that it runs in.
 */

public interface ApplicationContextAware extends Aware {
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
