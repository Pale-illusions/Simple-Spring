package com.iflove.simplespring.webmvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注Rest风格的异常处理器注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ResponseBody
@ControllerAdvice
public @interface RestControllerAdvice {
}
