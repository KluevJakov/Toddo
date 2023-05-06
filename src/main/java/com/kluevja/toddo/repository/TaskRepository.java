package com.kluevja.toddo.repository;

import com.kluevja.toddo.entity.Task;
import com.kluevja.toddo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(nativeQuery = true, value = "SELECT count(*) FROM tasks WHERE stage_id = 1")
    Long countNotify();
}
