package com.kluevja.toddo.entity.auth;

import com.kluevja.toddo.entity.Department;
import com.kluevja.toddo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

@Getter
@AllArgsConstructor
@ToString
public class JwtResponse {
    private String token;
    private String email;
    private Department department;
    private Collection<? extends GrantedAuthority> role;
    private long id;
}
