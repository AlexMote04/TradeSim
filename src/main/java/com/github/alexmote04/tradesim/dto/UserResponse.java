package com.github.alexmote04.tradesim.dto;

import com.github.alexmote04.tradesim.model.User;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String email
) {
    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}