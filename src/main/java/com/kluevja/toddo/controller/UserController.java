package com.kluevja.toddo.controller;

import com.kluevja.toddo.config.jwt.JwtTokenUtil;
import com.kluevja.toddo.entity.Role;
import com.kluevja.toddo.entity.User;
import com.kluevja.toddo.entity.auth.JwtRequest;
import com.kluevja.toddo.entity.auth.JwtResponse;
import com.kluevja.toddo.repository.DepartmentRepository;
import com.kluevja.toddo.repository.RoleRepository;
import com.kluevja.toddo.repository.UserRepository;
import com.kluevja.toddo.service.UserService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://0.0.0.0:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest user) {
        return userService.login(user);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @PostMapping("/importUsers")
    public ResponseEntity<?> importUsers(@RequestParam("file") MultipartFile file) throws IOException {
        Reader targetReader = new InputStreamReader(file.getInputStream());
        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(targetReader);
        List<List<String>> userRecords = new ArrayList<>();
        for (CSVRecord record : records) {
            userRecords.add(new ArrayList<>(List.of(record.values())));
        }
        targetReader.close();
        return userService.importUsers(userRecords);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(user);
    }

    @GetMapping
    public ResponseEntity<?> all(@RequestParam(required = false) Long depId) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(userService.getAll(depId, user.getId()));
    }
}
