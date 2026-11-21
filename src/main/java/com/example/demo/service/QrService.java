package com.example.demo.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.dto.UserMapper;
import com.example.demo.dto.ReturnUserDto;
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
@Slf4j
@AllArgsConstructor
public class QrService {

    private final QrRepository qrRepository;
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Transactional
    public ReturnUserDto createNewQrForUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + id + " не найден"));
        Qr qr = new Qr();
        user.addQr(qr);
        User savedUser = userRepository.save(user);
        log.info("Создан новый QR для пользователя с ID: " + savedUser.getId());
        return mapper.toReturnUserDto(savedUser);
    }

    @Transactional
    public void deleteQrForUser(UUID uuid) {
        Qr qr = qrRepository.findById(uuid)
                .orElseThrow(() -> new QrNotFoundException("Qr-код не найден"));
        User user = qr.getUser();
        if (user == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        user.removeQr(qr);
        User savedUser = userRepository.save(user);
        log.info("Удален Qr" + uuid + "для пользователя с ID:" + savedUser.getId());
    }
}
