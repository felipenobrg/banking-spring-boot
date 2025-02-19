package net.javaguide.banking.mapper;

import net.javaguide.banking.dto.UserDto;
import net.javaguide.banking.entity.User;

public class UserMapper {

    public static User mapToUser(UserDto UserDto) {
        User User = new User();
        User.setId(UserDto.getId());
        User.setEmail(UserDto.getEmail());
        User.setName(UserDto.getName());
        User.setPassword(UserDto.getPassword());

        return User;
    }

    public static UserDto mapToUserDto(User User) {
        return new UserDto(
                User.getId(),
                User.getEmail(),
                User.getName(),
                User.getPassword());
    }
}
