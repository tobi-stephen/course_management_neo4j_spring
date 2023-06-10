package dev.laplace.neo4j.dtos;

import dev.laplace.neo4j.models.Lesson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

    private String identifier;
    private String teacher;
    private String title;
    private boolean isEnrolled;
    private List<Lesson> lessons;

    public CourseDto(String identifier, String teacher, String title, boolean isEnrolled) {
        this.identifier = identifier;
        this.teacher = teacher;
        this.title = title;
        this.isEnrolled = isEnrolled;
    }
}
