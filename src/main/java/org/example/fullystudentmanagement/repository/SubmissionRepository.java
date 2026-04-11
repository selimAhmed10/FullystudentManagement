package org.example.fullystudentmanagement.repository;

import org.example.fullystudentmanagement.model.Course;
import org.example.fullystudentmanagement.model.Submission;
import org.example.fullystudentmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByCourse(Course course);
    List<Submission> findByStudent(User student);
    Optional<Submission> findByStudentAndCourse(User student, Course course);
}
