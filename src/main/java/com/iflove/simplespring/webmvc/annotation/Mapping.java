package com.iflove.simplespring.webmvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 用于表示 web mapping 的元注解
 */

@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapping {

}
