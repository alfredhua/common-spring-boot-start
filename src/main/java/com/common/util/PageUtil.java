package com.common.util;

import com.common.domain.response.PageBean;

/**
 * @author hua
 */
public class PageUtil<T> {

    public static<T> PageBean<T> validatePage(Integer pageNum, Integer pageSize,Integer offset){
        PageBean<T> pageResult=new PageBean<>();
        pageResult.setPage_num(pageNum);
        if(pageSize!=null){
            pageResult.setPage_size(pageSize);
        }
        pageResult.setOffset(offset);
        return pageResult;
    }
}
