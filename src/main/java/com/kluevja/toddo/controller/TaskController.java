package com.kluevja.toddo.controller;

import com.kluevja.toddo.entity.Stage;
import com.kluevja.toddo.entity.Task;
import com.kluevja.toddo.entity.dto.StatDto;
import com.kluevja.toddo.repository.TaskRepository;
import com.kluevja.toddo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://0.0.0.0:4200")
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Task task) {
        return taskService.createEvent(task);
    }

    @PostMapping("/process")
    public ResponseEntity<?> process(@RequestBody Task task) {
        return taskService.processIncident(task);
    }

    @GetMapping("/notifyCount")
    public ResponseEntity<?> countNotify() {
        return taskService.countNotify();
    }

    @GetMapping("/archive")
    public ResponseEntity<?> archive() {
        return taskService.findArchive();
    }

    @GetMapping("/stats")
    public ResponseEntity<?> stats() {
        ArrayList<StatDto> stats = new ArrayList<>();
        HashMap<String, Integer> statMap = new HashMap<>();
        taskService.findAll().forEach(e -> {
            statMap.merge(e.getEventWhat(), 1, Integer::sum);
        });

        statMap.forEach((key, value) -> {
            stats.add(new StatDto(key, value.toString()));
        });
        return ResponseEntity.ok().body(stats);
    }

    @GetMapping("/events")
    public ResponseEntity<?> tasksEvents() {
        return taskService.findEvents();
    }

    @GetMapping("/stageI")
    public ResponseEntity<?> tasksStageI() {
        return taskService.findStageI();
    }

    @GetMapping("/stageII")
    public ResponseEntity<?> tasksStageII() {
        return taskService.findStageII();
    }

    @GetMapping("/stageIII")
    public ResponseEntity<?> tasksStageIII() {
        return taskService.findStageIII();
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
