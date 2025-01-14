package com.iflove.simplespring.webmvc.metod.annotation;

import com.iflove.simplespring.webmvc.annotation.RequestHeader;
import com.iflove.simplespring.webmvc.exception.NotFoundExcpetion;
import com.iflove.simplespring.webmvc.handler.HandlerMethod;
import com.iflove.simplespring.webmvc.metod.support.HandlerMethodArgumentResolver;
import com.iflove.simplespring.webmvc.support.WebServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 获取请求头中的指定内容
 * author: Yusiheng
 */
public class RequestHeaderMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestHeader.class) && parameter.getParameterType() != Map.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, HandlerMethod handlerMethod, WebServletRequest webServletRequest, ConversionService conversionService) throws Exception {

        String name = "";
        final RequestHeader parameterAnnotation = parameter.getParameterAnnotation(RequestHeader.class);
        name = parameterAnnotation.value().equals("") ? parameter.getParameterName() : parameterAnnotation.value();
        final HttpServletRequest request = webServletRequest.getRequest();
        if (parameterAnnotation.require() && ObjectUtils.isEmpty(request.getHeader(name))) {
            throw new NotFoundExcpetion(handlerMethod.getPath() + "请求头没有携带" + name);
        }
        return conversionService.convert(request.getHeader(name), parameter.getParameterType());
    }
}
