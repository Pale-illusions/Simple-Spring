package com.iflove.simplespring.context.annotation;

import java.lang.annotation.*;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {

    String value() default "singleton";

}
