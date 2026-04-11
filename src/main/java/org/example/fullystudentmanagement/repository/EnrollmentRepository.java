package org.example.fullystudentmanagement.repository;

import org.example.fullystudentmanagement.model.Course;
import org.example.fullystudentmanagement.model.Enrollment;
import org.example.fullystudentmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {

    List<Enrollment> findByCourse(Course course);
    List<Enrollment> findByStudent(User user);
    Optional<Enrollment>findByStudentAndCourse(User user, Course course);
}
