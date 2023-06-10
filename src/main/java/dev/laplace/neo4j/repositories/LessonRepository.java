package dev.laplace.neo4j.repositories;

import dev.laplace.neo4j.models.Lesson;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends Neo4jRepository<Lesson, Long> {

    @Query("MATCH (:Course {identifier: $courseIdentifier})<-[:BELONGS_TO]-(lessons:Lesson) RETURN lessons")
    List<Lesson> findLessonsByCourseIdentifier(String courseIdentifier);

    Optional<Lesson> findLessonByIdentifier(String identifier);
}
