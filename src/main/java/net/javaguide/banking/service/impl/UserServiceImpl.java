package net.javaguide.banking.service.impl;

import net.javaguide.banking.dto.UserDto;
import net.javaguide.banking.mapper.UserMapper;
import net.javaguide.banking.repository.UserRepository;
import net.javaguide.banking.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto createUser(UserDto user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return UserMapper.mapToUserDto(userRepository.save(UserMapper.mapToUser(user)));
    }

}
