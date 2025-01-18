package com.iflove.simplespring.webmvc.metod.annotation;

import com.iflove.simplespring.webmvc.annotation.PathVariable;
import com.iflove.simplespring.webmvc.handler.HandlerMethod;
import com.iflove.simplespring.webmvc.metod.support.HandlerMethodArgumentResolver;
import com.iflove.simplespring.webmvc.support.WebServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 解析路径参数
 * author: Yusiheng
 */
public class PathVariableMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(PathVariable.class) && parameter.getParameterType() != Map.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, HandlerMethod handlerMethod, WebServletRequest webServletRequest, ConversionService conversionService) throws Exception {

        String name = "";
        Object result = null;
        // 1.获取 PathVariable 中的变量
        PathVariable parameterAnnotation = parameter.getParameterAnnotation(PathVariable.class);
        name = parameterAnnotation.value().equals("") ? parameter.getParameterName() : parameterAnnotation.value();
        Map<String, Object> resultMap = new HashMap<>();
        int index = -1;
        // 1.以/ 分割源path，找到变量 保存下标以及对应的变量
        final String path = handlerMethod.getPath();
        String[] split = path.split("/");
        for (int i = 0; i < split.length; i++) {
            final String s = split[i];
            if (s.contains("{") && s.contains("}") && s.contains(name)) {
                index = i;
                break;
            }
        }
        final HttpServletRequest request = webServletRequest.getRequest();
        // 2.以/ 分割请求path，根据上一步找到的下标，找到对应的值，放入resultMap
        split = request.getRequestURI().split("/");
        if (index != -1) {
            result = split[index];
        }
        Object o = conversionService.convert(result, parameter.getParameterType());
        return o;
    }
}
