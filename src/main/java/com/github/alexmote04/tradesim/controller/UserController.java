package com.github.alexmote04.tradesim.controller;

import com.github.alexmote04.tradesim.dto.RegisterUserRequest;
import com.github.alexmote04.tradesim.dto.UserResponse;
import com.github.alexmote04.tradesim.model.User;
import com.github.alexmote04.tradesim.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody RegisterUserRequest request) {
        // 1. Pass the DTO data to the Service layer
        User newUser = userService.registerUser(request.username(), request.email());

        // 2. Map the resulting Entity back to a safe DTO
        UserResponse response = UserResponse.fromEntity(newUser);

        // 3. Return a 201 CREATED HTTP status along with the data
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}