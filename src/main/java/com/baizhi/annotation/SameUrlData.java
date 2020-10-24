package com.baizhi.annotation;

import java.lang.annotation.*;

@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SameUrlData {
    /*
     * 接口限制时间
     * */
    long value() default 1000;
}
