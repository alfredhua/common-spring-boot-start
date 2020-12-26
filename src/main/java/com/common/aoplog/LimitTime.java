package com.common.aoplog;

import com.common.domain.constants.LimitTimeTypeEnum;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @auth guozhenhua
 * @date 2018/11/05
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LimitTime {

    /**
     * 最大限制数量，默认100个
     * @return
     */
    int limitValue() default 100;

    /**
     * 限制方式
     * @return
     */
    LimitTimeTypeEnum type() default LimitTimeTypeEnum.NULL;

    /**
     * 超时时间 默认300毫秒
     *
     * @return
     */
    long timeValues() default 300;

    /**
     * 时间单位
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    /**
     * 补偿方法，默认可以为空
     * @return
     */
    String fallback() default "";



}
