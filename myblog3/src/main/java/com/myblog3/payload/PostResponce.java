package com.myblog3.payload;

import lombok.Data;

import java.util.List;

@Data
public class PostResponce {

    private List<PostDto> postDto;
    private int pageNo;
    private int pageSize;
    private int totalPage;
    private long totalElement;
    private boolean last;

}
