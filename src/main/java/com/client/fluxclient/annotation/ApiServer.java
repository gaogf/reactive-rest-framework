package com.client.fluxclient.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
*@ClassName ApiServer
*@Description 服务器地址信息
*@Author gaogf
*@Date 2018/5/19 16:08
*@Version 1.0
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiServer {
    String value() default "";
}
