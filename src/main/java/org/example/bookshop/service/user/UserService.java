package org.example.bookshop.service.user;

import org.example.bookshop.dto.user.UserRegistrationRequestDto;
import org.example.bookshop.dto.user.UserResponseDto;
import org.example.bookshop.exception.RegistrationException;

public interface UserService {
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException;
}
