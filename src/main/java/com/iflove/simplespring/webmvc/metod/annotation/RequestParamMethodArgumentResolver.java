package com.iflove.simplespring.webmvc.metod.annotation;

import com.iflove.simplespring.webmvc.annotation.RequestBody;
import com.iflove.simplespring.webmvc.annotation.RequestParam;
import com.iflove.simplespring.webmvc.handler.HandlerMethod;
import com.iflove.simplespring.webmvc.metod.support.HandlerMethodArgumentResolver;
import com.iflove.simplespring.webmvc.support.WebServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 获取普通数据
 * author: Yusiheng
 */
public class RequestParamMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        final Class<?> parameterType = parameter.getParameterType();
        if (parameterType == HttpServletResponse.class || parameterType == HttpServletRequest.class) {
            return false;
        }
        if (isMultiPartFile(parameter)) {
            return false;
        }
        if (parameterType == Map.class) {
            return false;
        }
        if (parameter.hasParameterAnnotation(RequestBody.class)) {
            return false;
        }
        return true;
    }

    //需要处理对象，基本数据类型
    @Override
    public Object resolveArgument(MethodParameter parameter, HandlerMethod handlerMethod, WebServletRequest webServletRequest, ConversionService conversionService) throws Exception {

        final Class<?> parameterType = parameter.getParameterType();
        final HttpServletRequest request = webServletRequest.getRequest();
        // 基础数据类型，需要进行类型转换
        if (BeanUtils.isSimpleProperty(parameterType)) {
            String name = parameter.getParameterName();
            if (parameter.hasParameterAnnotation(RequestParam.class)) {
                final RequestParam parameterAnnotation = parameter.getParameterAnnotation(RequestParam.class);
                name = parameterAnnotation.value().equals("") ? parameter.getParameterName() : parameterAnnotation.value();
            }
            return conversionService.convert(request.getParameter(name), parameterType);

            // 对象,已经通过反射创建对象，就不需要类型转换了
        } else {

            // 0. 如果当前标注了RequestParam则报错
            if (parameter.hasParameterAnnotation(RequestParam.class)) {
                throw new IllegalArgumentException(handlerMethod.getBean().getClass().getName() + " " + handlerMethod.getMethod().getName() + "@RequestParam 不支持标注在对象上");
            }
            // 1. 获取所有参数
            final Map<String, String[]> parameterMap = request.getParameterMap();
            // 2. 创建对象
            final Object o = ReflectionUtils.accessibleConstructor(parameterType).newInstance();
            // 3. 遍历对象中的字段赋值
            final Field[] fields = parameterType.getDeclaredFields();
            initObject(parameterMap, o, fields, handlerMethod, conversionService);
            return o;
        }

    }

    public void initObject(Map<String, String[]> parameterMap, Object o, Field[] fields, HandlerMethod handlerMethod, ConversionService conversionService) throws Exception {
        for (Field field : fields) {
            final Class<?> type = field.getType();
            if (BeanUtils.isSimpleProperty(type)) {
                if (parameterMap.containsKey(field.getName())) {
                    field.setAccessible(true);
                    field.set(o, conversionService.convert(parameterMap.get(field.getName())[0], field.getType()));
                    field.setAccessible(false);
                }
            } else {
                final Object o1 = ReflectionUtils.accessibleConstructor(type).newInstance();
                // 属性填充
                Field[] fields1 = type.getDeclaredFields();
                initObject(parameterMap, o1, fields1, handlerMethod, conversionService);
                field.setAccessible(true);
                field.set(o, o1);
                field.setAccessible(false);
            }

        }
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
