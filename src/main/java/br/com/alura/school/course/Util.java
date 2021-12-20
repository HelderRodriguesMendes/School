package br.com.alura.school.course;

import br.com.alura.school.exception.BusinessRule;
import br.com.alura.school.exception.BusinessRuleReport;
import br.com.alura.school.registration.Registration;
import br.com.alura.school.registration.RegistrationRepository;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Util {
    private UserRepository userRepository;
    private CourseRepository courseRepository;
    private RegistrationRepository registrationRepository;

    public Util(UserRepository userRepository, CourseRepository courseRepository, RegistrationRepository registrationRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.registrationRepository = registrationRepository;
    }

    public List<CourseResponse> toListCourseResponse(List<Course> courses){
        List<CourseResponse>  courseResponseList = new ArrayList<>();
        courses.forEach(course -> {
            CourseResponse courseResponse = new CourseResponse(course);
            courseResponseList.add(courseResponse);
        });
        return courseResponseList;
    }

    public Registration checkRegistrationUserExist(String nameUser, String courseCode){
        Registration registration = new Registration();

        Optional<User> userOptional = userRepository.findByUsername(nameUser);
        if (userOptional.isEmpty()){
            throw new BusinessRule("The user entered was not found.");
        }

        Optional<Course> courseOptional = courseRepository.findByCode(courseCode);
        if(courseOptional.isEmpty()){
            throw new BusinessRule("The course entered was not found.");
        }

        Optional<Registration> registrationOptional = registrationRepository.findByUser_idAndCourse_id(userOptional.get().getId(), courseOptional.get().getId());
        if(registrationOptional.isPresent()){
            throw new BusinessRule(nameUser + " is already enrolled in " + courseCode);
        }

        registration.setUser(userOptional.get());
        registration.setCourse(courseOptional.get());
        registration.setDateRegistration(LocalDate.now());
        return registration;
    }

    public List<Report> getReport(){
        List<Report> reportList = new ArrayList<>();
        List<User> userList = userRepository.findAll();
        userList.forEach(user -> {
            Optional<Integer> quantityOptional = registrationRepository.getQuantityRecords(user.getId());
            if(quantityOptional.get() > 0){
                reportList.add(new Report(user.getEmail(), quantityOptional.get()));
            }
        });
        if(reportList.isEmpty()){
            throw new BusinessRuleReport("");
        }
        reportList.sort(Comparator.comparing(Report::getQuantidade_matriculas));
        return reportList;
    }
}
