package com.feesync.repository;

import com.feesync.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Student Repository
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Find student by roll number
     */
    Optional<Student> findByRollNumber(String rollNumber);

    /**
     * Check if student exists by roll number
     */
    boolean existsByRollNumber(String rollNumber);
}
