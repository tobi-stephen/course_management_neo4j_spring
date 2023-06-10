package dev.laplace.neo4j.models;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {


    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue
    private long id;

    private String identifier;
    private String title;
    private String teacher;

//    @Setter(AccessLevel.NONE)
//    @Relationship(type = "BELONGS_TO", direction = Relationship.Direction.INCOMING)
//    private List<Lesson> lessons;
}
