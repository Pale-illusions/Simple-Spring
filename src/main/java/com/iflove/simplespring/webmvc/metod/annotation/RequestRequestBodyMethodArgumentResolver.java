package com.iflove.simplespring.webmvc.metod.annotation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iflove.simplespring.webmvc.annotation.RequestBody;
import com.iflove.simplespring.webmvc.handler.HandlerMethod;
import com.iflove.simplespring.webmvc.metod.support.HandlerMethodArgumentResolver;
import com.iflove.simplespring.webmvc.support.WebServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

/**
 * 处理json，无需转换
 * author: Yusiheng
 */
public class RequestRequestBodyMethodArgumentResolver implements HandlerMethodArgumentResolver {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestBody.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, HandlerMethod handlerMethod, WebServletRequest webServletRequest, ConversionService conversionService) throws Exception {

        final String json = getJson(webServletRequest.getRequest());

        return objectMapper.readValue(json, parameter.getParameterType());
    }

    public String getJson(HttpServletRequest request) {

        final StringBuilder builder = new StringBuilder();
        String line = null;
        try (final BufferedReader reader = request.getReader()) {
            while (line != (line = reader.readLine())) {
                builder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.toString();
    }
}
