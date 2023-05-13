package com.kluevja.toddo.repository;

import com.kluevja.toddo.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM logs")
    List<Log> findByDay();
}
