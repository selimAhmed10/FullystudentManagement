package org.example.fullystudentmanagement.service;

import org.example.fullystudentmanagement.model.Course;
import org.example.fullystudentmanagement.model.Payment;
import org.example.fullystudentmanagement.model.User;
import org.example.fullystudentmanagement.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServicer {
    @Autowired
    private PaymentRepository paymentRepository;
    public PaymentServicer(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public List<Payment> getPaymentsByStudent(User student)
    {
        return paymentRepository.findByStudent(student);
    }

    public List<Payment> getPaymentByCourse(Course course)
    {
        return paymentRepository.findByCourse(course);
    }

    public Payment updatePaymentStatus(Long paymentId,String status)
    {
        Payment payment=paymentRepository.findById(paymentId).orElseThrow(() ->new RuntimeException("Payment Not Found"));
        payment.setStatus(status);
        return paymentRepository.save(payment);

    }

    public Payment createPayment(Payment payment)
    {
        return paymentRepository.save(payment);
    }

}
