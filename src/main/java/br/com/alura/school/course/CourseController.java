package br.com.alura.school.course;

import br.com.alura.school.registration.Registration;
import br.com.alura.school.registration.RegistrationRepository;
import br.com.alura.school.registration.UserRequest;
import br.com.alura.school.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Entity;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
class CourseController {

    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private Util util;

    CourseController(CourseRepository courseRepository,
                     RegistrationRepository registrationRepository,
                     UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.registrationRepository = registrationRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/courses")
    ResponseEntity<List<CourseResponse>> allCourses() {
        util = new Util(userRepository, courseRepository, registrationRepository);
        List<Course> courses = courseRepository.findAll();
        return new ResponseEntity<List<CourseResponse>>(util.toListCourseResponse(courses), HttpStatus.OK);
    }

    @GetMapping("/courses/{code}")
    ResponseEntity<CourseResponse> courseByCode(@PathVariable("code") String code) {
        Course course = courseRepository.findByCode(code).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("Course with code %s not found", code)));
        return ResponseEntity.ok(new CourseResponse(course));
    }

    @PostMapping("/courses")
    ResponseEntity<Void> newCourse(@RequestBody @Valid NewCourseRequest newCourseRequest) {
        courseRepository.save(newCourseRequest.toEntity());
        URI location = URI.create(format("/courses/%s", newCourseRequest.getCode()));
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/courses/{courseCode}/enroll")
    ResponseEntity<Registration>enroll(@RequestBody UserRequest request, @PathVariable String courseCode){
        util = new Util(userRepository, courseRepository, registrationRepository);
        Registration registration = util.checkRegistrationUserExist(request.getUsername(), courseCode);
        return new ResponseEntity<Registration>(registrationRepository.save(registration), HttpStatus.CREATED);
    }

    @GetMapping("/courses/enroll/report")
    ResponseEntity<List<Report>> report(){
        util = new Util(userRepository, courseRepository, registrationRepository);
        return new ResponseEntity<List<Report>>(util.getReport(), HttpStatus.OK);
    }
}
