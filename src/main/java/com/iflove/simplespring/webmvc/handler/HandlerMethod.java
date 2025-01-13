package com.iflove.simplespring.webmvc.handler;

import com.iflove.simplespring.webmvc.annotation.RequestMetod;
import org.springframework.core.MethodParameter;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class HandlerMethod {

    protected Object bean;

    protected Class type;

    protected Method method;

    protected String path;

    protected RequestMetod[] requestMethods = new RequestMetod[0];

    protected MethodParameter[] parameters = new MethodParameter[0];

    public HandlerMethod() {
    }

    public HandlerMethod(Object bean, Method method) {
        this.bean = bean;
        this.type = bean.getClass();
        this.method = method;
        final Parameter[] parameters = method.getParameters();
        MethodParameter[] methodParameters = new MethodParameter[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            methodParameters[i] = new MethodParameter(method,i);
        }
        this.parameters = methodParameters;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }


    //如果为空则设置所有请求类型
    public void setRequestMethods(RequestMetod[] requestMethods) {
        if (ObjectUtils.isEmpty(requestMethods)){
            requestMethods = RequestMetod.values();
        }
        this.requestMethods = requestMethods;
    }

    public MethodParameter[] getParameters() {
        return parameters;
    }

    public RequestMetod[] getRequestMethods() {
        return requestMethods;
    }

    public Method getMethod() {
        return method;
    }

    public Object getBean() {
        return bean;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {return true;}
        if (object == null || getClass() != object.getClass()) {return false;}
        HandlerMethod that = (HandlerMethod) object;
        return Objects.equals(path, that.path) && Arrays.equals(requestMethods, that.requestMethods);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(path);
        result = 31 * result + Arrays.hashCode(requestMethods);
        return result;
    }

    @Override
    public String toString() {
        return "HandlerMethod{" +
                "bean=" + bean +
                ", type=" + type +
                ", method=" + method +
                ", path='" + path + '\'' +
                ", requestMethods=" + Arrays.toString(requestMethods) +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }
}
