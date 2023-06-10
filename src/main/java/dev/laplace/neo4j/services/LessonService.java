package dev.laplace.neo4j.services;

import dev.laplace.neo4j.models.Lesson;
import dev.laplace.neo4j.repositories.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;

    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    public Lesson getLessonByIdentifier(String identifier) {
        return lessonRepository.findLessonByIdentifier(identifier)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Lesson> getAllLessonsByCourseIdentifier(String courseIdentifier) {
        return lessonRepository.findLessonsByCourseIdentifier(courseIdentifier);
    }
}
