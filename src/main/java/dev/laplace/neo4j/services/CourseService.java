package dev.laplace.neo4j.services;

import dev.laplace.neo4j.models.Course;
import dev.laplace.neo4j.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseByIdentifier(String identifier) {
        return courseRepository.findCourseByIdentifier(identifier)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Course getCourseByTeacher(String teacher) {
        return courseRepository.findCourseByTeacher(teacher)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
