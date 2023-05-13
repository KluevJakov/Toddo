package com.kluevja.toddo.repository;

import com.kluevja.toddo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE dep_id = ?1 and id != ?2")
    List<User> getAll(Long dep, Long currentUser);

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE dep_id = ?1")
    Optional<User> adminDepartmentAssigned(Long depId);

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE dep_id = ?1 LIMIT 1")
    User findEmployerWithDep(Long depId);
}
