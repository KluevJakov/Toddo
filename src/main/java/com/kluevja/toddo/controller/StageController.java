package com.kluevja.toddo.controller;

import com.kluevja.toddo.entity.Stage;
import com.kluevja.toddo.entity.auth.JwtRequest;
import com.kluevja.toddo.repository.StageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stage")
public class StageController {

    @Autowired
    private StageRepository stageRepository;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Stage stage) {
        return ResponseEntity.ok().body("stage create ok");
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody Stage stage) {
        return ResponseEntity.ok().body("stage delete ok");
    }

    @PostMapping("/rename")
    public ResponseEntity<?> rename(@RequestBody Stage stage) {
        return ResponseEntity.ok().body("stage rename ok");
    }
}
