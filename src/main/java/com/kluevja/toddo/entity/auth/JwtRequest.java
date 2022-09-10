package com.kluevja.toddo.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtRequest {
    private String email;
    private String password;
}
