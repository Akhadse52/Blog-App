package com.myblog3.payload;

import lombok.Data;

@Data
public class SignUpDto {

    private String email;
    private String username;
    private String password;
    private String name;
}
