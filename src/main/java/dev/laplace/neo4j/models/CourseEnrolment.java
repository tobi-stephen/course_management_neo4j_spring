package dev.laplace.neo4j.models;

import lombok.Data;

@Data
public class CourseEnrolment {
    private User user;
    private Course course;
}
