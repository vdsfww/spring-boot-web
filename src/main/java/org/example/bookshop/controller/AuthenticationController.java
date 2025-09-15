package org.example.bookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookshop.dto.user.UserLoginRequestDto;
import org.example.bookshop.dto.user.UserLoginResponseDto;
import org.example.bookshop.dto.user.UserRegistrationRequestDto;
import org.example.bookshop.dto.user.UserResponseDto;
import org.example.bookshop.exception.RegistrationException;
import org.example.bookshop.security.AuthenticationService;
import org.example.bookshop.service.user.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API for user registration")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    @Operation(summary = "User registration",
            description = "Creates a new user based on the provided data.")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }

    @PostMapping("/login")
    @Operation(summary = "Login a user",
            description = "Logs in a user and returns a JWT token.")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }
}
