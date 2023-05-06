package com.kluevja.toddo.controller;

import com.kluevja.toddo.entity.User;
import com.kluevja.toddo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/deps")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/")
    public ResponseEntity<?> all() {
        return ResponseEntity.ok().body(departmentService.findAllExcludeHead());
    }

    @GetMapping("/depInfo")
    public ResponseEntity<?> depInfo() {
        return ResponseEntity.ok().body(departmentService.getDepInfo());
    }
}
