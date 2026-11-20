package com.example.demo.dto;

import org.springframework.stereotype.Component;

import com.example.demo.entity.User;

@Component
public class DtoMapper {
    public UserDto toUserDto(User user){
        return new UserDto(user.getLastName(), user.getFirstName(),user.getMiddleName());
    }
}
