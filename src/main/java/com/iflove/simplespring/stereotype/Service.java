package com.iflove.simplespring.stereotype;

import com.iflove.simplespring.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Service {

    /**
     * Alias for {@link Component#value}.
     */
    @AliasFor(annotation = Component.class)
    String value() default "";

}
