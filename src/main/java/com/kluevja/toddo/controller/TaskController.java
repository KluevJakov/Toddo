package com.kluevja.toddo.controller;

import com.kluevja.toddo.entity.Stage;
import com.kluevja.toddo.entity.Task;
import com.kluevja.toddo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Task task) {
        return ResponseEntity.ok().body("task create ok");
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody Task task) {
        return ResponseEntity.ok().body("task delete ok");
    }

    @PostMapping("/rename")
    public ResponseEntity<?> rename(@RequestBody Task task) {
        return ResponseEntity.ok().body("task rename ok");
    }

    @PostMapping("/editDescription")
    public ResponseEntity<?> editDescription(@RequestBody Task task) {
        return ResponseEntity.ok().body("task editDescription ok");
    }

    @PostMapping("/setPerformer")
    public ResponseEntity<?> setPerformer(@RequestBody Task task) {
        return ResponseEntity.ok().body("task setPerformer ok");
    }

    @PostMapping("/setStage")
    public ResponseEntity<?> setStage(@RequestBody Task task) {
        return ResponseEntity.ok().body("task setStage ok");
    }

}
