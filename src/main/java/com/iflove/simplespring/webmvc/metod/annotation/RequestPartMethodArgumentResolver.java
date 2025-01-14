package com.iflove.simplespring.webmvc.metod.annotation;

import com.iflove.simplespring.webmvc.handler.HandlerMethod;
import com.iflove.simplespring.webmvc.metod.support.HandlerMethodArgumentResolver;
import com.iflove.simplespring.webmvc.multipartFile.MultipartFile;
import com.iflove.simplespring.webmvc.multipartFile.StandardMultipartFile;
import com.iflove.simplespring.webmvc.support.WebServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 文件上传解析器,已经解析过无需类型转换
 * author: Yusiheng
 */
public class RequestPartMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return isMultiPartFile(parameter);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, HandlerMethod handlerMethod, WebServletRequest webServletRequest, ConversionService conversionService) throws Exception {

        //拿到当前参数的名称和传入的名称匹配；
        final String parameterName = parameter.getParameterName();
        // 获取文件
        final HttpServletRequest request = webServletRequest.getRequest();
        final Collection<Part> parts = request.getParts();
        ArrayList<MultipartFile> files = new ArrayList<>();
        for (Part part : parts) {
            if (parameterName.equals(part.getName())) {
                if (!ObjectUtils.isEmpty(part)){
                    final StandardMultipartFile standardMultipartFile = new StandardMultipartFile(part, part.getSubmittedFileName());
                    files.add(standardMultipartFile);
                }
            }
            final Class<?> parameterType = parameter.getParameterType();
            if (parameterType == MultipartFile.class) {
                return files.get(0);
            }else if (parameterType == List.class || parameterType == Collection.class){
                return files;
            }else if (parameterType.isArray()){
                return files.toArray(new MultipartFile[files.size()]);
            }

        }
        return null;
    }

    // 单个/集合
    public boolean isMultiPartFile(MethodParameter parameter){
        final Class<?> parameterType = parameter.getParameterType();
        if (parameterType == MultipartFile.class){
            return true;
        }
        if (parameterType == List.class || parameterType == Collection.class){
            // 获取集合中的泛型
            Type genericParameterType = parameter.getGenericParameterType();
            ParameterizedType type = (ParameterizedType) genericParameterType;
            if(type.getActualTypeArguments()[0] == MultipartFile.class){
                return true;
            }
        }
        if (parameterType.isArray() && parameterType == MultipartFile.class){
            return true;
        }
        return false;
    }
}
