package com.example.demo.dto;

import org.springframework.stereotype.Component;

import com.example.demo.entity.User;

@Component
public class UserMapper {
    
    public UserDto toUserDto(User user) {
        return new UserDto(user.getLastName(), user.getFirstName(), user.getMiddleName());
    }

    public User toUser(UserDto userDto) {
        User user = new User();
        user.setLastName(userDto.lastName());
        user.setFirstName(userDto.firstName());
        user.setMiddleName(userDto.middleName());
        return user;
    }

    public ReturnUserDto toReturnUserDto(User user){
        return new ReturnUserDto(user.getId(), user.getLastName(), user.getFirstName(), user.getMiddleName());
    }
}
