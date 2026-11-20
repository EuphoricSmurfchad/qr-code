package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDto;
import com.example.demo.service.QrService;

import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/qr")
public class QrController {

    private final QrService service;

    public QrController(QrService service) {
        this.service = service;
    }

    @PostMapping("/{id}")
    public UserDto createNewQrForUser(@PathVariable Integer id) {

        return service.createNewQrForUser(id);
    }

    @DeleteMapping("/{qr}")
    public UserDto deleteQrForUser(@PathVariable UUID qr) {

        return service.deleteQrForUser(qr);
    }
}
