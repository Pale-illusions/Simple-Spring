package com.iflove.simplespring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 资源加载接口
 */

public interface Resource {
    /**
     * 获取资源加载流
     * @return 资源加载流
     */
    InputStream getInputStream() throws IOException;
}
