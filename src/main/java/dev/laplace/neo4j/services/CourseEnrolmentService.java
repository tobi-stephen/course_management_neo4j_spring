package dev.laplace.neo4j.services;

import dev.laplace.neo4j.models.Course;
import dev.laplace.neo4j.models.CourseEnrolment;
import dev.laplace.neo4j.repositories.CourseRepository;
import dev.laplace.neo4j.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CourseEnrolmentService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public Boolean getEnrolmentStatus(String username, String courseIdentifier) {
        return userRepository.findEnrolmentStatus(username, courseIdentifier);
    }

    public CourseEnrolment createEnrolment(String username, String courseIdentifier) {
        Boolean enrolled = getEnrolmentStatus(username, courseIdentifier);
        if (Objects.nonNull(enrolled) && enrolled) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already enrolled");
        }
        return userRepository.createCourseEnrolment(username, courseIdentifier);
    }

    public List<Course> getAllEnrolledCoursesByUsername(String username) {
        return courseRepository.findAllCoursesEnrolledByUsername(username);
    }
}
