package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ReturnUserDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.QrService;

import lombok.AllArgsConstructor;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/v1/qrs")
@AllArgsConstructor
public class QrController {

    private final QrService service;

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ReturnUserDto createNewQrForUser(@PathVariable Integer id) {
        return service.createNewQrForUser(id);
    }

    @DeleteMapping("/{qr}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQrForUser(@PathVariable UUID qr) {
        service.deleteQrForUser(qr);
    }
}
