package com.example.demo.service;

import java.util.UUID;

import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties.Apiversion.Use;
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
public class UserService {

    private final UserRepository userRepository;
    private final QrRepository qrRepository;
    private final DtoMapper mapper;

    public UserService(UserRepository userRepository, QrRepository qrRepository, DtoMapper mapper) {
        this.userRepository = userRepository;
        this.qrRepository = qrRepository;
        this.mapper = mapper;
    }

    @Transactional
    public UserDto findByQr(UUID uuid) {
        Qr qr = qrRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Qr-код не найден"));
        User user = qr.getUser();
        user.removeQr(qr);
        Qr newQr = new Qr();
        user.addQr(newQr);
        userRepository.saveAndFlush(user);
        return mapper.toUserDto(user);

    }

    @Transactional
    public Integer createUser(UserDto userDto) {
        User user = mapper.toUser(userDto);
        Qr qr = new Qr();
        user.addQr(qr);
        qr.setUser(user);
        User savedUser = userRepository.saveAndFlush(user);
        return savedUser.getId();
    }

    @Transactional
    public void deleteUserById(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с ID " + id + " не найден");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public UserDto updateUser(Integer id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Пользователь с ID " + id + " не найден"));
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setMiddleName(userDto.middleName());
        User savedUser = userRepository.save(user);
        return mapper.toUserDto(savedUser);
    }

    @Transactional
    public UserDto getUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Пользователь с ID " + id + " не найден"));
        return mapper.toUserDto(user);
    }

}
