package net.javaguide.banking.mapper;

import net.javaguide.banking.dto.UserDto;
import net.javaguide.banking.entity.User;

public class UserMapper {

    public static User mapToUser(UserDto UserDto) {
        User User = new User();
        User.setId(UserDto.getId());
        User.setEmail(UserDto.getEmail());
        User.setUsername(UserDto.getUsername());

        return User;
    }

    public static UserDto mapToUserDto(User User) {
        UserDto userDto = new UserDto();
        userDto.setId(User.getId());
        userDto.setEmail(User.getEmail());
        userDto.setUsername(User.getUsername());
        return userDto;
    }
}
