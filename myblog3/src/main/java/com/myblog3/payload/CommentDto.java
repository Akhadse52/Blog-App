package com.myblog3.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDto {

    private long id;
    @NotEmpty
    @Email(message = "Invalid Email Id")
    private String email;
    @NotEmpty
    @Size(min = 2,message = "Name Should be at least 2 character")
    private  String name;
    @NotEmpty
    @Size(min =10,message = "Comments Should be at least 10 character")
    private String body;
}
