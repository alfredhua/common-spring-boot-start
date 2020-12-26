package com.common.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequest implements java.io.Serializable {

    private Integer page_num;

    private Integer page_size=10;

    public Integer getPage_num() {
        if(page_num==null||page_num<1){
            page_num=1;
        }
        return page_num;
    }


    public Integer getOffset(){
        if(page_num==null||page_num<1){
            page_num=1;
        }
        return (page_num-1)*page_size;
    }

}
