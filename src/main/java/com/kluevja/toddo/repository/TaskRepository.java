package com.kluevja.toddo.repository;

import com.kluevja.toddo.entity.Task;
import com.kluevja.toddo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(nativeQuery = true, value = "SELECT count(*) FROM tasks WHERE stage_id = 1")
    Long countNotify();

    @Query(nativeQuery = true, value = "SELECT * FROM tasks WHERE stage_id = 9")
    List<Task> findArchive();

    @Query(nativeQuery = true, value = "SELECT * FROM tasks WHERE stage_id = 2")
    List<Task> findStageI();

    @Query(nativeQuery = true, value = "SELECT * FROM tasks WHERE stage_id = 3 OR stage_id = 4")
    List<Task> findStageII();

    @Query(nativeQuery = true, value = "SELECT * FROM tasks WHERE stage_id = 5 OR stage_id = 6 OR stage_id = 7 OR stage_id = 8")
    List<Task> findStageIII();

    @Query(nativeQuery = true, value = "SELECT * FROM tasks WHERE stage_id = 1")
    List<Task> findEvents();

    @Query(nativeQuery = true, value = "SELECT reg_number FROM tasks")
    List<Long> findRegNumbers();
}
