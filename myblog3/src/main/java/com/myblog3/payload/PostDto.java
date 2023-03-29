package com.myblog3.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private long id;

    @NotNull
    @Size(min = 2 , message = "Post Title Should be More then 2 Character")
    private String title;
    @NotNull
    @Size(min=10,message = "Post Description Should be at least 10 character or More")
    private String description;
    @NotNull
    @NotEmpty
    private String content;
}
