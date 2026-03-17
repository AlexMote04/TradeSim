package com.github.alexmote04.tradesim.dto;

public record RegisterUserRequest(
        String username,
        String email
) {}