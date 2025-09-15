package org.example.bookshop.service.user;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.bookshop.dto.user.UserRegistrationRequestDto;
import org.example.bookshop.dto.user.UserResponseDto;
import org.example.bookshop.exception.RegistrationException;
import org.example.bookshop.mapper.UserMapper;
import org.example.bookshop.model.RoleName;
import org.example.bookshop.model.User;
import org.example.bookshop.repository.role.RoleRepository;
import org.example.bookshop.repository.user.UserRepository;
import org.example.bookshop.service.shoppingcart.ShoppingCartService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ShoppingCartService shoppingCartService;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException("Email address already in use");
        }
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRoles(Set.of(roleRepository.findByName(RoleName.USER)));
        userRepository.save(user);
        shoppingCartService.createShoppingCart(user);
        return userMapper.toResponseDto(user);

    }
}
