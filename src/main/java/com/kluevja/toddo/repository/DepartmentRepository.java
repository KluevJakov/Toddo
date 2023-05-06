package com.kluevja.toddo.repository;

import com.kluevja.toddo.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM deps WHERE id != 1")
    List<Department> findAllExcludeHead();
}
