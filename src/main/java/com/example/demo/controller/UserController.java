package com.example.demo.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ReturnUserDto;
import com.example.demo.dto.UserDto;

import com.example.demo.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ReturnUserDto> enterByQr(@PathVariable UUID qr) {
        ReturnUserDto returnUserDto = service.enterByQr(qr);
        URI location = URI.create("api/v1/users/" + returnUserDto.id());
        return ResponseEntity.created(location)
                .header("Custom-Header", "Value")
                .body(returnUserDto);
    }

    @PostMapping
    public ResponseEntity<ReturnUserDto> createUser(@Valid @RequestBody UserDto userDto) {
        ReturnUserDto returnUserDto = service.createUser(userDto);
        URI location = URI.create("api/v1/users/" + returnUserDto.id());
        return ResponseEntity.created(location)
                .header("Custom-Header", "Value")
                .body(returnUserDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Integer id) {
        service.deleteUserById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReturnUserDto> updateUser(@PathVariable Integer id, @RequestBody UserDto userDto) {
        ReturnUserDto returnUserDto = service.updateUser(id, userDto);
        URI location = URI.create("api/v1/users/" + returnUserDto.id());
        return ResponseEntity.created(location)
                .header("Custom-Header", "Value")
                .body(returnUserDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnUserDto> getUser(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getUser(id));
    }

}
