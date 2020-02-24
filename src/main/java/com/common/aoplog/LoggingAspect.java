package com.common.aoplog;


import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;

/**
 * @auth guozhenhua
 * @date 2018/11/05
 */

@Aspect
@Component
public class LoggingAspect {


    private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("@annotation(com.common.aoplog.Logging)")
    public void cut() {
    }


    @Around("cut()")
    public Object advice(ProceedingJoinPoint joinPoint) {
        long startTime = System.currentTimeMillis();
        StringBuffer stringBuffer = new StringBuffer();
        Method method = getMethod(joinPoint);
        stringBuffer.append("请求方法名称:" + method.getName() + ",");
        java.util.List args = filter(joinPoint.getArgs(), method);
        if (args!=null&&args.size() > 0) {
            for (Object arg : args) {
                stringBuffer.append("请求参数:" + JSON.toJSON(arg)+ ",");
            }
        }
        Object object=null;
        try {
             object = joinPoint.proceed();
        }catch (Throwable e){
            e.printStackTrace();
        }
        if (null != object) {
            stringBuffer.append("返回数据:" + JSON.toJSON(object) + ",");
        }
        stringBuffer.append("请求耗时:" + (System.currentTimeMillis() - startTime) + "耗秒.");
        logger.info(stringBuffer.toString());
        return object;
    }

    private Method getMethod(ProceedingJoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        if (!(signature instanceof MethodSignature)){
            throw new IllegalArgumentException("该注解只能用在方法上");
        }
        MethodSignature methodSignature = (MethodSignature) signature;
        return methodSignature.getMethod();
    }

    private java.util.List filter(Object[] args, Method targetMethod) {
        Annotation[][] annotationList = targetMethod.getParameterAnnotations();
        List list=new ArrayList();
        for (int i = 0; i < annotationList.length; i++) {
            for (int j = 0; j < annotationList[i].length; j++) {
                if (annotationList[i][j].annotationType() == RequestBody.class||
                        annotationList[i][j].annotationType() == PathVariable.class) {
                    list.add(args[i]);
                }
            }
        }
        return list;
    }

}
