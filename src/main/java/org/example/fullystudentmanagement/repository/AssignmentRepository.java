package org.example.fullystudentmanagement.repository;

import org.example.fullystudentmanagement.model.Assignment;
import org.example.fullystudentmanagement.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByCourse(Course course);
}