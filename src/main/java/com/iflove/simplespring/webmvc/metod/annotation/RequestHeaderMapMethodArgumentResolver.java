package com.iflove.simplespring.webmvc.metod.annotation;

import com.iflove.simplespring.webmvc.annotation.RequestHeader;
import com.iflove.simplespring.webmvc.handler.HandlerMethod;
import com.iflove.simplespring.webmvc.metod.support.HandlerMethodArgumentResolver;
import com.iflove.simplespring.webmvc.support.WebServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取所有请求头中的内容
 * author: Yusiheng
 */
public class RequestHeaderMapMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.hasParameterAnnotation(RequestHeader.class) && parameter.getParameterType() == Map.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, HandlerMethod handlerMethod, WebServletRequest webServletRequest, ConversionService conversionService) throws Exception {

        final HttpServletRequest request = webServletRequest.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> resultMap = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            final String key = headerNames.nextElement();
            final String value = request.getHeader(key);
            resultMap.put(key, value);
        }


        return resultMap;
    }
}
