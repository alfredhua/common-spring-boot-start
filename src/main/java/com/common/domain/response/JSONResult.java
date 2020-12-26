package com.common.domain.response;

import lombok.Data;

/**
 * @author hua
 */
@Data
public class JSONResult<T> implements java.io.Serializable {


    public static String SUCCESS="SUCCESS";

    private static String FAIL="FAIL";


    private String msg;

    private  String code;

    private T data;

    public JSONResult() {
        this.code=SUCCESS;
        this.msg="请求成功";
        this.data=null;
    }

    public JSONResult(T data) {
        this.code=SUCCESS;
        this.msg="请求成功";
        this.data = data;
    }

    public JSONResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data=null;
    }

    public static<T> JSONResult<T> success() {
        return new JSONResult();
    }

    public static<T> JSONResult<T> success(T data) {
        return new JSONResult(data);
    }

    public static<T> JSONResult<T> error(String code, String msg){
        return new JSONResult(code,msg);
    }

    public static<T> JSONResult<T> editError(){
        return new JSONResult("EDIT_ERROR","更新失败");
    }

    public static<T> JSONResult<T> createError(){
        return new JSONResult("CREATE_ERROR","创建失败");
    }

    public static<T> JSONResult<T> getError(){
        return new JSONResult("GET_ERROR","获取失败");
    }

    public static<T> JSONResult<T> delError(){
        return new JSONResult("DEL_ERROR","删除失败");
    }

    public static<T> JSONResult<T> fail(){
        return new JSONResult(FAIL,"请求失败");
    }

}
