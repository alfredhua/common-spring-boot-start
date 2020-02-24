package com.common.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JSONPageResult<T> implements java.io.Serializable {

    public static String SUCCESS="SUCCESS";

    private static String FAIL="FAIL";


    private String msg;

    private  String code;

    PageBean<T> data;

    public  JSONPageResult(PageBean<T> data) {
        this.code=SUCCESS;
        this.msg="请求成功";
        this.data = data;
    }

    private  JSONPageResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public static<T> JSONPageResult<T> listError(){
        return new JSONPageResult("LIST_ERROR","列表获取失败");
    }

    public static JSONPageResult error(String code, String msg) {
        return new JSONPageResult(code,msg);
    }

    @SuppressWarnings(value = "unchecked")
    public static<T> JSONPageResult success(PageBean<T> data) {
        return new JSONPageResult(data);
    }





}
