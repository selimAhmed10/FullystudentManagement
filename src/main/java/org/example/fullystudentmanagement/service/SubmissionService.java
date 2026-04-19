package org.example.fullystudentmanagement.service;

import org.example.fullystudentmanagement.model.Course;
import org.example.fullystudentmanagement.model.Submission;
import org.example.fullystudentmanagement.model.User;
import org.example.fullystudentmanagement.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    public SubmissionService(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    public List<Submission> findAll() {
        return submissionRepository.findAll();
    }

    public List<Submission> getAllSubmissionByStudent(User student) {
        return submissionRepository.findByStudent(student);
    }

    public List<Submission> getAllSubmissionByCourse(Course course) {

        return submissionRepository.findByCourse(course);
    }

    public Optional<Submission> getSubmissionByStudentAndCourse(User student, Course course) {
        return submissionRepository.findByStudentAndCourse(student, course);
    }

    public Submission gradeSubmission(Long submissionId,Integer grade,String feedback)
    {
        Submission submission= submissionRepository.findById(submissionId).orElseThrow(() -> new RuntimeException("Submission Not Found"));
        submission.setGrade(grade);
        submission.setFeedback(feedback);
        return submissionRepository.save(submission);
    }



}
