package com.kluevja.toddo.service;

import com.kluevja.toddo.entity.Sheet;
import com.kluevja.toddo.entity.User;
import com.kluevja.toddo.repository.SheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SheetService {
    @Autowired
    private SheetRepository sheetRepository;

    //TODO: check it for correction
    public boolean validateSheet (Sheet sheet) {
        return !sheet.getTitle().isEmpty() && !sheet.getDescription().isEmpty() && sheet.getCreator() != null;
    }

    public void create (Sheet sheet) {
        sheetRepository.save(sheet);
    }

    public List<Sheet> getMyPersonalSheets (User user) {
        return sheetRepository.findAll().stream()
                .filter(e -> !e.getIsGroup())
                .filter(e -> e.getCreator().getId() == user.getId())
                .collect(Collectors.toList());
    }

    public Sheet getSheet(long id, User currentUser) throws Exception {
        Optional<Sheet> sheet = sheetRepository.findById(id);

        if (sheet.isPresent()) {
            //TODO: add group membership check
            if (sheet.get().getCreator().getId() == currentUser.getId()) {
                return sheet.get();
            } else {
                throw new Exception("No access for this sheet");
            }
        } else {
            throw new Exception("Sheet not found");
        }
    }
}
