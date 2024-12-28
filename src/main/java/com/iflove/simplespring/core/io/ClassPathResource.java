package com.iflove.simplespring.core.io;

import com.iflove.simplespring.utils.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 类路径下资源加载
 */

public class ClassPathResource implements Resource {
    private final String path;

    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, null);
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        this.path = path;
        this.classLoader = Objects.nonNull(classLoader) ? classLoader : ClassUtils.getDefaultClassLoader();
    }

    /**
     * 获取资源加载流
     * @return 资源加载流
     */
    @Override
    public InputStream getInputStream() throws IOException {
        InputStream inputStream = classLoader.getResourceAsStream(path);
        if (Objects.isNull(inputStream)) {
            throw new FileNotFoundException(this.path + " cannot be opened because it does not exist");
        }
        return inputStream;
    }
}
