package com.iflove.simplespring.core.io;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 资源加载包装接口
 */

public interface ResourceLoader {
    String CLASSPATH_URL_PRRFIX = "classpath:";

    /**
     * 定义资源获取接口
     * @param location 地址
     * @return 资源获取接口
     */
    Resource getResource(String location);
}
