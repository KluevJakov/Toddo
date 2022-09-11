package com.kluevja.toddo.controller;

import com.kluevja.toddo.config.jwt.JwtTokenUtil;
import com.kluevja.toddo.entity.Role;
import com.kluevja.toddo.entity.User;
import com.kluevja.toddo.entity.auth.JwtRequest;
import com.kluevja.toddo.entity.auth.JwtResponse;
import com.kluevja.toddo.entity.response.UserResponse;
import com.kluevja.toddo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtTokenUtil jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest user) {
        System.out.println(user);
        Optional<User> userAttempt = userRepository.findByEmail(user.getEmail());
        if (userAttempt.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: User not found!");
        } else if (!encoder.matches(user.getPassword(), userAttempt.get().getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Incorrect password");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        System.out.println("generateJwtToken "+jwt);
        return ResponseEntity.ok(new JwtResponse(jwt, userAttempt.get().getEmail(), userAttempt.get().getRole().getAuthority()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Passwords don't matches!");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(new UserResponse(user.getId(), user.getEmail(), user.getRole().getAuthority()));
    }
}
