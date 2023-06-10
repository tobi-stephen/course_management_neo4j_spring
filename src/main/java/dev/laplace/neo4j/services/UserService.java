package dev.laplace.neo4j.services;

import dev.laplace.neo4j.models.User;
import dev.laplace.neo4j.models.UserRequest;
import dev.laplace.neo4j.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(UserRequest userRequest) {
        if (userRepository.findUserByUsername(userRequest.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username already exists");
        }

        User user = new User();
        user.setName(userRequest.getName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRoles(userRequest.getRoles());
        user.setUsername(userRequest.getUsername());
        userRepository.save(user);

        return user;
    }

    public User updateUser(String username, UserRequest userRequest) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));

        // only permitted to update roles and name properties
        user.setName(userRequest.getName());
        user.setRoles(userRequest.getRoles());
        userRepository.save(user);

        return user;
    }
}
