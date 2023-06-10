package dev.laplace.neo4j.controllers;

import dev.laplace.neo4j.dtos.CourseDto;
import dev.laplace.neo4j.dtos.CourseEnrolmentDto;
import dev.laplace.neo4j.dtos.UserDto;
import dev.laplace.neo4j.models.Course;
import dev.laplace.neo4j.models.CourseEnrolment;
import dev.laplace.neo4j.models.CourseEnrolmentRequest;
import dev.laplace.neo4j.services.CourseEnrolmentService;
import dev.laplace.neo4j.services.LessonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/enrolments")
@RequiredArgsConstructor
public class CourseEnrolmentController {
    private final CourseEnrolmentService courseEnrolmentService;
    private final LessonService lessonService;
    private final ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<CourseEnrolmentDto> createEnrolment(Principal principal, @RequestBody CourseEnrolmentRequest request) {
        CourseEnrolment enrolment = courseEnrolmentService.createEnrolment(principal.getName(), request.getCourseIdentifier());
        CourseEnrolmentDto courseEnrolmentDto = new CourseEnrolmentDto();
        courseEnrolmentDto.setCourseDto(modelMapper.map(enrolment.getCourse(), CourseDto.class));
        courseEnrolmentDto.getCourseDto().setEnrolled(true);
        courseEnrolmentDto.getCourseDto().setLessons(lessonService.getAllLessonsByCourseIdentifier(enrolment.getCourse().getIdentifier()));
        courseEnrolmentDto.setUserDto(modelMapper.map(enrolment.getUser(), UserDto.class));
        return new ResponseEntity<>(courseEnrolmentDto, HttpStatus.OK);
    }

    @GetMapping(value = "/status")
    public ResponseEntity<Boolean> getEnrolmentStatus(Principal principal, @RequestBody CourseEnrolmentRequest request) {
        Boolean enrolled = courseEnrolmentService.getEnrolmentStatus(principal.getName(), request.getCourseIdentifier());
        return new ResponseEntity<>(Objects.nonNull(enrolled) && enrolled, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<CourseDto>> getAllEnrolledCourses(Principal principal) {
        List<Course> courses = courseEnrolmentService.getAllEnrolledCoursesByUsername(principal.getName());
        List<CourseDto> courseDtos = courses.stream().map(
                (course) -> new CourseDto(course.getIdentifier()
                        , course.getTeacher()
                        , course.getTitle()
                        , true
                        , lessonService.getAllLessonsByCourseIdentifier(course.getIdentifier())
                )
        ).toList();
        return new ResponseEntity<>(courseDtos, HttpStatus.OK);
    }
}
