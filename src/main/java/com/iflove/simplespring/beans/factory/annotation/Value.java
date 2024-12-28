package com.iflove.simplespring.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 * Annotation at the field or method/constructor parameter level
 * that indicates a default value expression for the affected argument.
 */

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {

    /**
     * The actual value expression: e.g. "#{systemProperties.myProp}".
     */
    String value();

}
