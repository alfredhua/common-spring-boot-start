package com.common.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RedisUtils {


  public RedisUtils(ValueOperations<String, Object> objectValueOperation, ValueOperations<String, String> stringValueOperation,
                    ValueOperations<String, java.util.List> listValueOperation) {
    this.objectValueOperation = objectValueOperation;
    this.stringValueOperation = stringValueOperation;
    this.listValueOperation = listValueOperation;
  }

  private long defaultTimeOut=30*24*60;

  private ValueOperations<String,Object> objectValueOperation;

  private ValueOperations<String,String> stringValueOperation;

  private ValueOperations<String, java.util.List> listValueOperation;


  /**
   * Object实体类型存储
   * @param key  key
   * @param timeout 秒
   * @param t    value
   * @return
   */
  public void  set(String key,long timeout, Object t){
    try {
      if (timeout==0) {
        objectValueOperation.set(key, t, defaultTimeOut, TimeUnit.SECONDS);
      } else{
        objectValueOperation.set(key, t, timeout, TimeUnit.SECONDS);
      }
    }catch (Exception e){
      throw new RuntimeException("set error",e);
    }
  }

  public void expire(String key,long timeout){
    if (timeout==0) {
      objectValueOperation.getOperations().expire(key, defaultTimeOut, TimeUnit.SECONDS);
    }else{
      objectValueOperation.getOperations().expire(key, timeout, TimeUnit.SECONDS);
    }
  }

  /**
   * Object类型获取
   * @param key
   * @param tClass
   * @param <T>
   * @return
   */
  public <T>  T get(String key,Class<T> tClass)  {
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      Object o = objectValueOperation.get(key);
      return mapper.readValue(mapper.writeValueAsString(o), tClass);
    }catch ( Exception e){
      throw new RuntimeException("redis get error",e);
    }
  }


  public boolean del(String key) {
    if ("".equals(key)){
      return false;
    }else {
      Boolean delete = objectValueOperation.getOperations().delete(key);
      if (delete == null) {
        return true;
      } else {
        return delete;
      }
    }

  }

  /**
   * String类型存储
   * @param key
   * @param timeout
   * @param value
   */
  public void setStr(String key,long timeout,String value){
    try {
      if (timeout==0) {
        stringValueOperation.set(key, value, defaultTimeOut, TimeUnit.SECONDS);
      }else{
        stringValueOperation.set(key, value, timeout, TimeUnit.SECONDS);
      }
    }catch (Exception e){
      throw new RuntimeException("set error",e);
    }
  }

  /**
   * String 类型获取
   * @param key
   * @return
   */
  public String  getStr(String key){
    try {
      return stringValueOperation.get(key);
    }catch ( Exception e){
      throw new RuntimeException("redis get error",e);
    }
  }



  /**
   * Object实体类型存储
   * @param key
   * @param timeout
   * @param t
   * @return
   */
  public void  setList(String key,Long timeout, java.util.List t){
    try {
      if (timeout==null) {
        listValueOperation.set(key, t, defaultTimeOut, TimeUnit.SECONDS);
      } else{
        listValueOperation.set(key, t, timeout, TimeUnit.SECONDS);
      }
    }catch (Exception e){
      throw new RuntimeException("set error",e);
    }
  }

  /**
   * Object类型获取
   * @param key
   * @param <T>
   * @return
   */
  public<T> List<T> getList(String key)  {
    try {
      return listValueOperation.get(key);
    }catch ( Exception e){
      throw new RuntimeException("redis get error",e);
    }
  }
}
