package com.kluevja.toddo.repository;

import com.kluevja.toddo.entity.Sheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SheetRepository extends JpaRepository<Sheet, Long> {
}
