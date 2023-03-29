package com.myblog3.payload;

import lombok.Data;

@Data
public class LoginDto {

    private String userNameOrEmail;
    private String password;
}
