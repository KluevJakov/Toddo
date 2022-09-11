package com.kluevja.toddo.entity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class UserResponse {
    private long id;
    private String email;
    private String role;
}
