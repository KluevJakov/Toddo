package com.kluevja.toddo.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class JwtRequest {
    private String email;
    private String password;
}
