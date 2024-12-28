package com.iflove.simplespring.core.io;

import cn.hutool.core.lang.Assert;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 资源加载包装默认实现
 */

public class DefaultResourceLoader implements ResourceLoader {

    /**
     * 定义资源获取接口
     * @param location 地址
     * @return 资源获取接口
     */
    @Override
    public Resource getResource(String location) {
        Assert.notNull(location, "Location must not be null");
        // 类路径
        if (location.startsWith(CLASSPATH_URL_PRRFIX)) {
            return new ClassPathResource(location.substring(CLASSPATH_URL_PRRFIX.length()));
        } else {
            try {
                // URL解析
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException e) {
                // 文件路径
                return new FileSystemResource(location);
            }
        }
    }
}
