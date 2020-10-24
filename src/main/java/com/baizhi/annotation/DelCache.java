package com.baizhi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)  //修饰范围，方法
@Retention(RetentionPolicy.RUNTIME)    //生命周期 运行时
public @interface DelCache {
    //String value(); //注解上必须加 value = "";
    //String name() default ""; //注解上 可加可不加 name = ""; 不加就是默认值""
}
