package org.example.fullystudentmanagement.controler;

import org.example.fullystudentmanagement.model.*;
import org.example.fullystudentmanagement.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/student")
@PreAuthorize("hasRole('STUDENT)")
public class StudentController {

    private final UserService userService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final AssignmentService assignmentService;
    private final PaymentServicer paymentServicer;
    private final SubmissionService submissionService;

    public StudentController(UserService userService,CourseService courseService,EnrollmentService enrollmentService,AssignmentService assignmentService,PaymentServicer paymentServicer,SubmissionService submissionService) {
        this.userService = userService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.assignmentService = assignmentService;
        this.paymentServicer = paymentServicer;
        this.submissionService = submissionService;
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(@AuthenticationPrincipal UserDetails currentUser) {
        User student = userService.findByEmail(currentUser.getUsername()).orElseThrow();
        return ResponseEntity.ok().body(student);
    }

    @GetMapping("/courses")
    public List<Course> getAllCourses()
    {
        return courseService.getAllCourses();
    }

    @PostMapping("/enroll/{courseId}")

    public ResponseEntity<Enrollment> enrollCourse(@PathVariable Long courseId, @AuthenticationPrincipal UserDetails currentUser)
    {
        User student= userService.findByEmail(currentUser.getUsername()).orElseThrow();
        Course course=courseService.getCourseById(courseId).orElseThrow();
        Enrollment enrollment=enrollmentService.enrollment(student,course);
        return new ResponseEntity<>(enrollment, HttpStatus.OK);
    }

    @GetMapping("/my-courses")
    public List<Enrollment> getMyEnrollments(@AuthenticationPrincipal UserDetails currentUser) {
        User student = userService.findByEmail(currentUser.getUsername()).orElseThrow();
        return enrollmentService.getEnrollmentsByStudent(student);
    }

    @GetMapping("/schedule")
    public List<Enrollment> getSchedule(@AuthenticationPrincipal UserDetails currentUser)
    {
        return getMyEnrollments(currentUser);
    }

    @GetMapping("/assignments")
    public List<Assignment> getMyAssignments(@AuthenticationPrincipal UserDetails currentUser)
    {
        User student = userService.findByEmail(currentUser.getUsername()).orElseThrow();
        List<Enrollment> enrollments=enrollmentService.getEnrollmentsByStudent(student);
        List <Assignment> assignments=new ArrayList<>();
        for(Enrollment e:enrollments)
        {
            assignments.addAll(assignmentService.getAssignmentByCourse(e.getCourse()));
        }
        return assignments;
    }

    @PostMapping("/submissions")
    public ResponseEntity<Submission> submitAssignment(@AuthenticationPrincipal UserDetails currentUser, @RequestBody Submission submission)
    {
        User student= userService.findByEmail(currentUser.getUsername()).orElseThrow();
        submission.setStudent(student);
        submission.setSubmittedAt(LocalDateTime.now());
        return  new ResponseEntity<>(submissionService.createSubmission(submission), HttpStatus.CREATED);
    }


    @GetMapping("/my-submission")
    public List<Submission> getMySubmission(@AuthenticationPrincipal UserDetails currentUser)
    {
        User student = userService.findByEmail(currentUser.getUsername()).orElseThrow();
        return submissionService.getAllSubmissionByStudent(student);
    }

    @PostMapping("/deposit")
    public ResponseEntity<Payment> depositCourseFee(@RequestParam Long courseId,
                                                    @RequestParam Double amount,
                                                    @AuthenticationPrincipal UserDetails currentUser) {
        User student = userService.findByEmail(currentUser.getUsername()).orElseThrow();
        Course course = courseService.getCourseById(courseId).orElseThrow();
        Payment payment = new Payment();
        payment.setStudent(student);
        payment.setCourse(course);
        payment.setAmount(amount);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus("COMPLETED");
        Payment saved = paymentServicer.createPayment(payment);

        // এনরোলমেন্টের পেমেন্ট স্ট্যাটাস true করে দেওয়া
        enrollmentService.getEnrollmentByStudentAndCourse(student, course)
                .ifPresent(enrollment -> enrollmentService.updatePaymentStatus(enrollment.getId(), true));

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/my-payments")
    public List<Payment>  getMyPayments(@AuthenticationPrincipal UserDetails currentUser)
        {
        User student = userService.findByEmail(currentUser.getUsername()).orElseThrow();
        return paymentServicer.getPaymentsByStudent(student);
        }


}
