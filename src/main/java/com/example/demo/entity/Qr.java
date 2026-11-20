package com.example.demo.entity;

import java.util.UUID;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users_qr")
public class Qr {

    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    @Column(name = "qr_code", nullable = false)
    private UUID userQr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Qr() {
    }

    public UUID getUserQr() {
        return userQr;
    }

    public void setUserQr(UUID userQr) {
        this.userQr = userQr;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
