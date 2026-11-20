package com.example.demo.service;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.DtoMapper;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Qr;
import com.example.demo.entity.User;
import com.example.demo.repository.QrRepository;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class QrService {

    private final QrRepository qrRepository;
    private final UserRepository userRepository;
    private final DtoMapper mapper;

    public QrService(QrRepository qrRepository, UserRepository userRepository, DtoMapper mapper) {
        this.qrRepository = qrRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Transactional
    public UserDto createNewQrForUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с ID " + id + " не найден"));
        Qr qr = new Qr();
        user.addQr(qr);
        User savedUser = userRepository.saveAndFlush(user);
        return mapper.toUserDto(savedUser);
    }

    @Transactional
    public UserDto deleteQrForUser(UUID uuid) {
        Qr qr = qrRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Qr-код не найден"));
        User user = qr.getUser();
        user.removeQr(qr);
        User savedUser = userRepository.saveAndFlush(user);
        return mapper.toUserDto(savedUser);
    }

}
