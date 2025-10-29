package com.placement.repository;

import com.placement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(User.Role role);
    List<User> findByDepartment(String department);
    List<User> findByBatch(String batch);
    
    @Query("SELECT u FROM User u WHERE u.role = 'STUDENT' AND u.cgpa >= ?1")
    List<User> findStudentsByCgpaGreaterThan(Double cgpa);
    
    @Query("SELECT u FROM User u WHERE u.role = 'STUDENT' AND u.department IN ?1")
    List<User> findStudentsByDepartments(List<String> departments);
}