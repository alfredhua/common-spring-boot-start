package com.common.aoplog;

import java.lang.annotation.*;

/**
 * @auth guozhenhua
 * @date 2018/11/05
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Logging {

}
