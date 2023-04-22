package com.kluevja.toddo.controller;

import com.kluevja.toddo.config.jwt.JwtTokenUtil;
import com.kluevja.toddo.entity.Role;
import com.kluevja.toddo.entity.User;
import com.kluevja.toddo.entity.auth.JwtRequest;
import com.kluevja.toddo.entity.auth.JwtResponse;
import com.kluevja.toddo.repository.DepartmentRepository;
import com.kluevja.toddo.repository.RoleRepository;
import com.kluevja.toddo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtTokenUtil jwtUtils;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private DepartmentRepository depsRepository;


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
        return ResponseEntity.ok(new JwtResponse(jwt, userAttempt.get().getEmail(), userAttempt.get().getAuthorities(), userAttempt.get().getId()));
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
        user.setActive(true);
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        user.getRoles().add(roleRepository.findById(1L).get());
        user.setDepartment(depsRepository.findById(8L).get());
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(user);
    }
}
