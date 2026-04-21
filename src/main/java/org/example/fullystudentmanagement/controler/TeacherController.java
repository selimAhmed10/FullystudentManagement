package org.example.fullystudentmanagement.controler;


import org.example.fullystudentmanagement.model.*;
import org.example.fullystudentmanagement.service.AssignmentService;
import org.example.fullystudentmanagement.service.CourseService;
import org.example.fullystudentmanagement.service.SubmissionService;
import org.example.fullystudentmanagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teacher")
@PreAuthorize("hasRole('TEACHER')")
public class TeacherController {
    private final CourseService courseService;
    private final UserService userService;
    private final AssignmentService assignmentService;
    private final SubmissionService submissionService;

    public TeacherController(CourseService courseService, UserService userService,
                             AssignmentService assignmentService, SubmissionService submissionService) {
        this.courseService = courseService;
        this.userService = userService;
        this.assignmentService = assignmentService;
        this.submissionService = submissionService;
    }

    // FIXED: Get all students - returns List directly (not ResponseEntity)
    @GetMapping("/students")
    public List<User> getAllStudents() {
        List<User> allUsers = userService.findAllUsers();
        return allUsers.stream()
                .filter(u -> u.getRole() == Role.STUDENT)
                .collect(Collectors.toList());
    }

    // FIXED: Method name "getMyCourses" (not "getMyCouse")
    @GetMapping("/courses")
    public List<Course> getMyCourses(@AuthenticationPrincipal UserDetails currentUser) {
        User teacher = userService.findByEmail(currentUser.getUsername()).orElseThrow();
        List<Course> allCourses = courseService.getAllCourses();
        return allCourses.stream()
                .filter(c -> c.getTeacher() != null && c.getTeacher().getId().equals(teacher.getId()))
                .collect(Collectors.toList());
    }

    @GetMapping("/assignments/course/{courseId}")
    public List<Assignment> getAssignmentByCourse(@PathVariable Long courseId) {
        Course course = courseService.getCourseById(courseId).orElseThrow();
        return assignmentService.getAssignmentByCourse(course);
    }

    @PostMapping("/assignments")
    public ResponseEntity<Assignment> createAssignment(@RequestBody Assignment assignment,
                                                       @AuthenticationPrincipal UserDetails currentUser) {
        User teacher = userService.findByEmail(currentUser.getUsername()).orElseThrow();
        assignment.setTeacher(teacher);
        Assignment created = assignmentService.createAssignment(assignment);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // FIXED: Method name "updateAssignment" (not "updateAassignment")
    @PutMapping("/assignments/{id}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable Long id,
                                                       @RequestBody Assignment assignment) {
        Optional<Assignment> existing = assignmentService.getAssignmentById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        assignment.setId(id);
        Assignment updated = assignmentService.updateAssignment(assignment);
        return ResponseEntity.ok(updated);
    }

    // FIXED: Method name "deleteAssignment" (not "deleteAassignment")
    @DeleteMapping("/assignments/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        Optional<Assignment> existing = assignmentService.getAssignmentById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        assignmentService.deleteAssignmentById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/submissions/assignment/{assignmentId}")
    public List<Submission> getSubmissionByAssignment(@PathVariable Long assignmentId) {
        Assignment assignment = assignmentService.getAssignmentById(assignmentId).orElseThrow();
        return submissionService.getAllSubmissionByAssignment(assignment);
    }

    @PutMapping("/submissions/{submissionId}/grade")
    public ResponseEntity<Submission> gradeSubmission(@PathVariable Long submissionId,
                                                      @RequestParam Integer grade,
                                                      @RequestParam(required = false) String feedback) {
        Submission graded = submissionService.gradeSubmission(submissionId, grade, feedback);
        return ResponseEntity.ok(graded);
    }
}