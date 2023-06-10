package dev.laplace.neo4j.controllers;

import dev.laplace.neo4j.dtos.CourseDto;
import dev.laplace.neo4j.models.Course;
import dev.laplace.neo4j.services.CourseEnrolmentService;
import dev.laplace.neo4j.services.CourseService;
import dev.laplace.neo4j.services.LessonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final LessonService lessonService;
    private final CourseEnrolmentService courseEnrolmentService;

    @GetMapping("/")
    public ResponseEntity<List<CourseDto>> courseIndex(Principal principal) {
        List<Course> courses = courseService.getAllCourses();
        List<CourseDto> courseDtos = courses.stream().map(
                (course) -> new CourseDto(course.getIdentifier()
                        , course.getTeacher()
                        , course.getTitle()
                        , (principal == null) || courseEnrolmentService.getEnrolmentStatus(principal.getName(), course.getIdentifier())
                        , lessonService.getAllLessonsByCourseIdentifier(course.getIdentifier())
                )
        ).toList();
        return new ResponseEntity<>(courseDtos, HttpStatus.OK);
    }

    @GetMapping("/{identifier}/")
    public ResponseEntity<CourseDto> getCourseByIdentifier(Principal principal, @PathVariable(value = "identifier") String identifier) {
        Course course = courseService.getCourseByIdentifier(identifier);
        CourseDto courseDto = new CourseDto( course.getIdentifier()
                , course.getTeacher()
                , course.getTitle()
                , (principal == null) || courseEnrolmentService.getEnrolmentStatus(principal.getName(), course.getIdentifier())
                , lessonService.getAllLessonsByCourseIdentifier(course.getIdentifier()));

        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }
}
