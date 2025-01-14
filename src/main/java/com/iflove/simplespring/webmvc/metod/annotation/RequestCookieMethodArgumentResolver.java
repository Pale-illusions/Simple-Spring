package com.iflove.simplespring.webmvc.metod.annotation;

import com.iflove.simplespring.webmvc.annotation.Cookie;
import com.iflove.simplespring.webmvc.exception.NotFoundExcpetion;
import com.iflove.simplespring.webmvc.handler.HandlerMethod;
import com.iflove.simplespring.webmvc.metod.support.HandlerMethodArgumentResolver;
import com.iflove.simplespring.webmvc.support.WebServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;

import javax.servlet.http.HttpServletRequest;

/**
 * 解析Cookie当中的参数
 * author: Yusiheng
 */
public class RequestCookieMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Cookie.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, HandlerMethod handlerMethod, WebServletRequest webServletRequest, ConversionService conversionService) throws Exception {

        final Cookie parameterAnnotation = parameter.getParameterAnnotation(Cookie.class);
        String name = "";
        name = parameterAnnotation.value().equals("") ? parameter.getParameterName() : parameterAnnotation.value();
        final HttpServletRequest request = webServletRequest.getRequest();
        // 获取所有Cookie
        final javax.servlet.http.Cookie[] cookies = request.getCookies();
        // 遍历拿值
        for (javax.servlet.http.Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return conversionService.convert(cookie.getValue(), parameter.getParameterType());
            }
        }
        if (parameterAnnotation.require()) {
            throw new NotFoundExcpetion(handlerMethod.getPath() + "cookie没有携带" + name);
        }
        return null;
    }
}
