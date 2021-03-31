package com.common.domain.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PageRequest implements Serializable {

    private Integer pageNum;

    private Integer pageSize=10;

    public Integer getPageNum() {
        if(pageNum==null||pageNum<1){
            pageNum=1;
        }
        return pageNum;
    }


    public Integer getOffset(){
        if(pageNum==null||pageNum<1){
            pageNum=1;
        }
        return (pageNum-1)*pageSize;
    }

}
