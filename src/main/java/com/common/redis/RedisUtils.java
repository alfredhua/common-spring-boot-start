package com.common.redis;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RedisUtils {

  private static final long DEFAULT_TIME_OUT=30*24*60;

  private RedisTemplate<String,Object> template;

  public RedisUtils(RedisTemplate<String, Object> template) {
    this.template = template;
  }


  /**
   * 设置过期时间，默认单位秒
   * @param key key
   * @param timeout 过期时间
   */
  public void expire(String key,long timeout){
    if (timeout==0) {
      template.expire(key, DEFAULT_TIME_OUT, TimeUnit.SECONDS);
    }else{
      template.expire(key, timeout, TimeUnit.SECONDS);
    }
  }

  /**
   * 根据key 获取过期时间
   * @param key 键 不能为null
   * @return 时间(秒) 返回0代表为永久有效
   */
  public Long getExpire(String key) {
    return template.getExpire(key, TimeUnit.SECONDS);
  }

  /**
   * 验证key是否存在
   * @param key key
   */
  public Boolean hasKey(String key) {
    return template.hasKey(key);
  }

  /**
   * 删除
   * @param key key
   */
  public void del(String... key) {
    if (key != null && key.length > 0) {
      if (key.length == 1) {
        template.delete(key[0]);
      } else {
        template.delete(CollectionUtils.arrayToList(key));
      }
    }
  }

  /**
   * Object类型存储
   * @param key  key
   * @param timeout 过期时间，0：表示使用默认30天,null：表示永不过期
   * @param value
   */
  public void set(String key,Long timeout,Object value){
    try {
      if (timeout==null){
        template.opsForValue().set(key, value);
      }else if (timeout==0) {
        template.opsForValue().set(key, value, DEFAULT_TIME_OUT, TimeUnit.SECONDS);
      }else{
        template.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
      }
    }catch (Exception e){
      throw new RuntimeException("set error",e);
    }
  }
  /**
   * Object 类型获取
   * @param key
   * @return
   */
  public Object get(String key){
    try {
      return template.opsForValue().get(key);
    }catch ( Exception e){
      throw new RuntimeException("redis get error",e);
    }
  }

  /**
   * 递增
   * @param key  key
   * @param delta 递增因子，0或者null时候：默认是1,负数表示递减。
   * @return
   */
  public Long increment(String key, Long delta) {
    if(delta==null||delta==0){
      return template.opsForValue().increment(key, 1L);
    }
    return template.opsForValue().increment(key, delta);
  }


  /**
   * HashGet
   *
   * @param key  键 不能为null
   * @param field 项 不能为null
   * @return 值
   */
  public Object hashGet(String key, String field) {
    return template.opsForHash().get(key, field);
  }

  /**
   * 向一张hash表中放入数据,如果不存在将创建
   * @param key   键
   * @param field  项
   * @param value 值
   */
  public void hashSet(String key, String field, Object value) {
      template.opsForHash().put(key, field, value);
  }

  /**
   *
   * @param key key
   * @param map 对象
   */
  public void hashPutAll(String key, Map<Object, Object> map){
    template.opsForHash().putAll(key,map);
  }

  /**
   * 获取map
   * @param key
   * @return
   */
  public Map<Object, Object> hashGetAll(String key){
    return template.opsForHash().entries(key);
  }

  /**
   * 返回这个key里面所有fields的值
   * @param key
   * @param fields
   * @return
   */
  public List<Object> multiHashGet(String key, String...fields) {
    return template.opsForHash().multiGet(key, CollectionUtils.arrayToList(fields));
  }






  /**
   * 从redis中随机返回一个key
   * @return
   */
  public Object randomKey() {
    return template.randomKey();
  }

  /**
   * 返回 key 所储存的值的类型
   * @param key
   * @return
   */
  public DataType getType(String key) {
    return template.type(key);
  }



}
