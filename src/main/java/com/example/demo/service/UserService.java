package com.example.demo.service;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.example.demo.dto.UserMapper;
import com.example.demo.dto.ReturnUserDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Qr;
import com.example.demo.entity.User;
import com.example.demo.exception.QrNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.QrRepository;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j 
public class UserService {

    private final UserRepository userRepository;
    private final QrRepository qrRepository;
    private final UserMapper mapper;

    @Transactional
    public ReturnUserDto enterByQr(UUID uuid) {
        Qr qr = qrRepository.findById(uuid)
                .orElseThrow(() -> new QrNotFoundException( "Qr-код не найден"));
        User user = qr.getUser();
        user.removeQr(qr);
        Qr newQr = new Qr();
        user.addQr(newQr);
        userRepository.saveAndFlush(user);
        log.info("Пользователь вошел, новый QR добавлен, старый удален");
        return mapper.toReturnUserDto(user);

    }

    @Transactional
    public ReturnUserDto createUser(UserDto userDto) {
        User user = mapper.toUser(userDto);
        Qr qr = new Qr();
        user.addQr(qr);
        qr.setUser(user);
        User savedUser = userRepository.saveAndFlush(user);
        log.info("Пользователь создан, ID: " + savedUser.getId());
        return mapper.toReturnUserDto(savedUser);
    }

    @Transactional
    public void deleteUserById(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Пользователь с ID " + id + " не найден");
        }
        userRepository.deleteById(id);
        log.info("Пользователь удален, ID: " + id);
    }

    @Transactional
    public ReturnUserDto updateUser(Integer id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + id + " не найден"));
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setMiddleName(userDto.middleName());
        User savedUser = userRepository.save(user);
        log.info("Пользователь обновлен, ID: " + savedUser.getId());
        return mapper.toReturnUserDto(savedUser);
    }

    @Transactional
    public ReturnUserDto getUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + id + " не найден"));
        return mapper.toReturnUserDto(user);
    }

}
