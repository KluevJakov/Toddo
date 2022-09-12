package com.kluevja.toddo.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class JwtResponse {
    private String token;
    private String email;
    private String role;
    private long id;
}
