package dev.laplace.neo4j.controllers;

import dev.laplace.neo4j.dtos.UserDto;
import dev.laplace.neo4j.models.User;
import dev.laplace.neo4j.models.UserRequest;
import dev.laplace.neo4j.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/me")
    public ResponseEntity<String> loggedInUser(Principal principal) {
        return new ResponseEntity<>(principal.getName(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> signUp(@RequestBody UserRequest userRequest) {
        User user = userService.createUser(userRequest);
        UserDto userDto = new UserDto(user.getName(), user.getUsername(), user.getRoles());
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PutMapping("/me")
    public ResponseEntity<UserDto> updateUser(Principal principal, @RequestBody UserRequest userRequest) {
        User user = userService.updateUser(principal.getName(), userRequest);
        return new ResponseEntity<>(modelMapper.map(user, UserDto.class), HttpStatus.CREATED);
    }
}
