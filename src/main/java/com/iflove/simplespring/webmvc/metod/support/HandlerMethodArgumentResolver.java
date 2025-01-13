package com.iflove.simplespring.webmvc.metod.support;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;

public interface HandlerMethodArgumentResolver {

	boolean supportsParameter(MethodParameter parameter);

	Object resolveArgument(MethodParameter parameter, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception;
}
