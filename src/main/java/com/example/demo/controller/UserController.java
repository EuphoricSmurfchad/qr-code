package com.example.demo.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ReturnUserDto;
import com.example.demo.dto.UserDto;

import com.example.demo.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor 
public class UserController {

    private final UserService service;

    @PostMapping("/enter/{qr}")
    public ReturnUserDto enterByQr(@PathVariable UUID qr) {
        return service.enterByQr(qr);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReturnUserDto createUser(@Valid @RequestBody UserDto userDto) {
        return service.createUser(userDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Integer id) {
        service.deleteUserById(id);
    }

    @PutMapping("/{id}")
    public ReturnUserDto updateUser(@PathVariable Integer id, @RequestBody UserDto userDto) {
        return service.updateUser(id, userDto);
    }

    @GetMapping("/{id}")
    public ReturnUserDto getUser(@PathVariable Integer id) {
        return service.getUser(id);
    }

}
