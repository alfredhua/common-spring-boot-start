package com.common.exception;

import com.common.constants.SysErrorCodeEnum;
import com.common.mail.MailProperties;
import com.common.mail.MailUtils;
import com.common.response.JSONResult;
//import com.common.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static java.lang.Thread.currentThread;

@ControllerAdvice
public class ExceptionHandlerAdvice {


    @Autowired
    MailUtils mailUtils;

    @Autowired
    MailProperties mailProperties;

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public JSONResult exceptionHandler(HttpServletRequest httpServletRequest, Exception ex) {
        StringBuilder errorData = getErrorData(ex);
        String requestURI = httpServletRequest.getRequestURI();
        //TODO 缺少请求的参数
        errorData.append("请求地址:<br>" + requestURI + "<br> 请求参数:<br>" + "" + "<br>");
        java.util.List<String> toMailList=new ArrayList<>();
        toMailList.add(mailProperties.getTo_mail());
//        if(!envConfig.isDevActive()) {
//            try {
//                mailUtils.sendMail(toMailList, "错误故障", errorData.toString());
//            } catch (Exception e) {
//                throw new RuntimeException("error",e);
//            }
//        }
        return JSONResult.error(SysErrorCodeEnum.ERR_SYSTEM.getCode(),SysErrorCodeEnum.ERR_SYSTEM.getMsg());
    }

    private StringBuilder getErrorData(Throwable ex){
        StringBuilder error=new StringBuilder();
        java.io.OutputStream ops=new ByteArrayOutputStream();
        ex.printStackTrace(new PrintStream(ops));
        error.append(ops.toString()).append("<br>");
        error.append("线程号:").append(currentThread().getId())
            .append("，线程名:").append(currentThread().getName()).append("<br>");
        return error;
    }

}
