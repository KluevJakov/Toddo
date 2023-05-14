package com.kluevja.toddo.controller;

import com.kluevja.toddo.entity.Log;
import com.kluevja.toddo.repository.LogRepository;
import com.kluevja.toddo.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/logs")
public class LogController {
    @Autowired
    private LogService logService;

    @GetMapping
    public ResponseEntity<?> logs(@RequestParam String date) {
        List<Log> logs = logService.findByDay().stream()
                .filter(e -> e.getDate().getYear() == Integer.parseInt(date.substring(0,4))-1900)
                .filter(e -> e.getDate().getMonth() == Integer.parseInt(date.substring(5,7))-1)
                .filter(e -> e.getDate().getDate() == Integer.parseInt(date.substring(8,10)))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(logs);
    }
}
