package org.example.fullystudentmanagement.service;

import org.example.fullystudentmanagement.model.Assignment;
import org.example.fullystudentmanagement.model.Course;
import org.example.fullystudentmanagement.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public Assignment createAssignment(Assignment assignment)
    {
        return assignmentRepository.save(assignment);
    }

    public List<Assignment> getAssignmentByCourse(Course course)
    {
        return assignmentRepository.findByCourse(course);
    }

    public Optional<Assignment>getAssignmentById(Long id)
    {
        return assignmentRepository.findById(id);
    }

    public List<Assignment> getAllAssignments()
    {
        return assignmentRepository.findAll();
    }

    public Assignment updateAssignment(Assignment assignment)
    {
        return assignmentRepository.save(assignment);
    }

    public void deleteAssignmentById(Long id)

        {
        assignmentRepository.deleteById(id);
        }

}
