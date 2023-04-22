package com.kluevja.toddo.controller;

import com.kluevja.toddo.entity.Sheet;
import com.kluevja.toddo.entity.User;
import com.kluevja.toddo.service.SheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/sheet")
public class SheetController {

    @Autowired
    private SheetService sheetService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Sheet sheet) {
        if (sheetService.validateSheet(sheet)) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            sheetService.create(sheet);
            return ResponseEntity.ok("{}");
        }
        return ResponseEntity.badRequest().body("You can't create sheet");
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody Sheet sheet) {
        return ResponseEntity.ok().body("sheet delete ok");
    }

    @PostMapping("/rename")
    public ResponseEntity<?> rename(@RequestBody Sheet sheet) {
        return ResponseEntity.ok().body("sheet rename ok");
    }

    @PostMapping("/editDescription")
    public ResponseEntity<?> editDescription(@RequestBody Sheet sheet) {
        return ResponseEntity.ok().body("sheet editDescription ok");
    }

    @GetMapping("/personal/my")
    public ResponseEntity<?> getMyPersonalSheets() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(sheetService.getMyPersonalSheets(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSheetById(@PathVariable("id") Long sheetId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            Sheet sheet = sheetService.getSheet(sheetId, user);
            return ResponseEntity.ok().body(sheet);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
