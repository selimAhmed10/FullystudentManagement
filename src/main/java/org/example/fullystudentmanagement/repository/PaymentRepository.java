package org.example.fullystudentmanagement.repository;

import org.example.fullystudentmanagement.model.Course;
import org.example.fullystudentmanagement.model.Payment;
import org.example.fullystudentmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByCourse(Course course);
    List<Payment> findByStudent(User student);
}