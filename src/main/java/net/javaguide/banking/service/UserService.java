package net.javaguide.banking.service;

import net.javaguide.banking.dto.UserDto;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto login(UserDto userDto);
}
