package dev.laplace.neo4j.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserRequest {
    private String name;
    private String username;
    private String password;
    private String roles;
}
