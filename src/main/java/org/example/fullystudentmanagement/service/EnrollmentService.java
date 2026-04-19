package org.example.fullystudentmanagement.service;

import org.example.fullystudentmanagement.model.Course;
import org.example.fullystudentmanagement.model.Enrollment;
import org.example.fullystudentmanagement.model.User;
import org.example.fullystudentmanagement.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public Enrollment enrollment(User student, Course course)
    {
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollment.setPaid(false);
        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> findByStudent(User student)
    {
        return enrollmentRepository.findByStudent(student);
    }

    public List<Enrollment> findByCourse(Course course)
        {
        return enrollmentRepository.findByCourse(course);
        }

        public Optional<Enrollment> getEnrollmentByStudentAndCourse(User student, Course course)
        {
            return enrollmentRepository.findByStudentAndCourse(student, course);
        }

        public void updatePaymentStatus(Long enrollmentId, boolean paid)
        {
            enrollmentRepository.findById(enrollmentId).ifPresent(e -> {
                e.setPaid(paid);
                enrollmentRepository.save(e);

            });
        }


}
