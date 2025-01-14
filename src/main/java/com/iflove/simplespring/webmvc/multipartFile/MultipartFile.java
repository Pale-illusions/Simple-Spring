package com.iflove.simplespring.webmvc.multipartFile;

import org.springframework.core.io.InputStreamSource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 自定义文件类型接口
 * author: Yusiheng
 */
public interface MultipartFile extends InputStreamSource {

    // 获取名称
    String getName();

    // 获取文件名
    String getOriginalFilename();

    // 获取类型
    String getContentType();

    // 是否为空
    boolean isEmpty();

    // 大小
    long getSize();

    // 大小
    byte[] getBytes() throws IOException;

    // 获取流
    @Override
    InputStream getInputStream() throws IOException;

    // 转化
    void transferTo(File dest) throws IOException, IllegalStateException;

    default void transferTo(Path dest) throws IOException, IllegalStateException {
        FileCopyUtils.copy(getInputStream(), Files.newOutputStream(dest));
    }

}
