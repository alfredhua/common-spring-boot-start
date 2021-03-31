package com.common.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class PageBean<T> implements Serializable {


    private int page_num=0;

    private long total;

    private int page_size=10;

    private int offset;

    private List<T> list;


}
