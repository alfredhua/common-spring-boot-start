package com.common.domain.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageBean<T> implements java.io.Serializable {


    private int page_num=0;

    private long total;

    private int page_size=10;

    private int offset;

    private java.util.List<T> list;


}
