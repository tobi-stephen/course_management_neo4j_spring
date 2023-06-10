package dev.laplace.neo4j.repositories;

import dev.laplace.neo4j.models.CourseEnrolment;
import dev.laplace.neo4j.models.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.Optional;

public interface UserRepository extends Neo4jRepository<User, Long> {

    Optional<User> findUserByUsername(String username);

    @Query("MATCH (user:User), (course:Course) WHERE user.username = $username AND course.identifier = $courseIdentifier "
            + "RETURN EXISTS ((user)-[:ENROLLED_IN]->(course))")
    Boolean findEnrolmentStatus(String username, String courseIdentifier);

    @Query("MATCH (user:User), (course:Course) WHERE user.username = $username AND course.identifier = $courseIdentifier "
            + "CREATE (user)-[:ENROLLED_IN]->(course) RETURN user, course")
    CourseEnrolment createCourseEnrolment(String username, String courseIdentifier);
}
