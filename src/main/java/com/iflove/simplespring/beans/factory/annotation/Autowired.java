package com.iflove.simplespring.beans.factory.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 * Marks a constructor, field, setter method or config method as to be
 * autowired by Spring's dependency injection facilities.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD})
public @interface Autowired {
}
