package org.example.fullystudentmanagement.controler;

import org.example.fullystudentmanagement.model.Course;
import org.example.fullystudentmanagement.model.User;
import org.example.fullystudentmanagement.service.CourseService;
import org.example.fullystudentmanagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController
{
    private final UserService userService;
    private final CourseService courseService;

    public  AdminController(UserService userService, CourseService courseService)
    {
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping("/users")
   public List<User>  findAllUsers()
    {
        return userService.findAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id)
    {
        return userService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<User> createuser(@RequestBody User user)
    {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        if (!userService.findById(id).isPresent()) return ResponseEntity.notFound().build();
        user.setId(id);
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userService.findById(id).isPresent()) return ResponseEntity.notFound().build();
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/courses")
    public List<Course> getAllCourses()
    {
        return courseService.getAllCourses();
    }

    @GetMapping("/courses/{id]")
        public ResponseEntity<Course> getCourseById(@PathVariable Long id)
        {
            return courseService.getCourseById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

    @PostMapping("/courses")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        return new ResponseEntity<>(courseService.craeteCourse(course), HttpStatus.CREATED);
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course)
    {
      if (!courseService.getCourseById(id).isPresent()) return ResponseEntity.notFound().build();
      course.setId(id);
      return new ResponseEntity<>(courseService.update(course), HttpStatus.OK);
    }

    @DeleteMapping("/courses/{id]")
    public ResponseEntity<Course> deleteCourse(@PathVariable Long id)
    {
        if (!courseService.getCourseById(id).isPresent()) return ResponseEntity.notFound().build();
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }





}
