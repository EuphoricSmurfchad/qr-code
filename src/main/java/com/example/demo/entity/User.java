package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity 
@Table (name = "users")
public class User {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column (name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column (name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column (name = "middle_name", length = 100)
    private String middleName;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Qr> qrCodes = new ArrayList<>();

    public User() {
    }

    public User(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void addQr(Qr qr){
        qrCodes.add(qr);
        qr.setUser(this);
    }

    public void removeQr(Qr qr){
        qrCodes.remove(qr);
        qr.setUser(null);
    }

}
