package com.iflove.simplespring.webmvc.annotation;

import java.lang.annotation.*;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface RequestMapping {

    String value() default "";

}
