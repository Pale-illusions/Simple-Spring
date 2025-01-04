package com.iflove.simplespring.tx.annotation;

import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Transactional {

    Class<? extends Throwable>[] rollbackFor() default {};
}
