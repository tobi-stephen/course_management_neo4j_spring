package dev.laplace.neo4j.dtos;

import lombok.Data;

@Data
public class CourseEnrolmentDto {
    UserDto userDto;
    CourseDto courseDto;
}
