package com.iflove.simplespring.webmvc.metod.annotation;

import com.iflove.simplespring.webmvc.annotation.RequestBody;
import com.iflove.simplespring.webmvc.handler.HandlerMethod;
import com.iflove.simplespring.webmvc.metod.support.HandlerMethodArgumentResolver;
import com.iflove.simplespring.webmvc.support.WebServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取所有普通数据
 * author: Yusiheng
 */
public class RequestParamMapMethodArgumentResolver implements HandlerMethodArgumentResolver {


    // 写RequireParam 不写都可以，会遇到其他的也有不写的场景，HttpServletRequest Mule... 需要枚举
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        final Class<?> parameterType = parameter.getParameterType();
        if (parameterType == HttpServletResponse.class || parameterType == HttpServletRequest.class) {
            return false;
        }
        if (isMultiPartFile(parameter)) {
            return false;
        }
        if (parameter.hasParameterAnnotation(RequestBody.class)) {
            return false;
        }
        return parameterType == Map.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, HandlerMethod handlerMethod, WebServletRequest webServletRequest, ConversionService conversionService) throws Exception {

        HashMap<Object, Object> resultMap = new HashMap<>();

        final HttpServletRequest request = webServletRequest.getRequest();
        final Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((k, v) -> {
            resultMap.put(k, v[0]);
        });

        return resultMap;
    }

    public boolean isMultiPartFile(MethodParameter parameter) {
        final Class<?> parameterType = parameter.getParameterType();
        if (parameterType == MultipartFile.class) {
            return true;
        }
        if (parameterType == List.class || parameterType == Collection.class) {
            // 获取集合中的泛型
            Type genericParameterType = parameter.getGenericParameterType();
            ParameterizedType type = (ParameterizedType) genericParameterType;
            if (type.getActualTypeArguments()[0] == MultipartFile.class) {
                return true;
            }
        }
        if (parameterType.isArray() && parameterType == MultipartFile.class) {
            return true;
        }
        return false;
    }


}
