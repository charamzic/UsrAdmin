package com.charamza.usradmin.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String surname;
    private String email;
    private String phone;
    private boolean isActive;
    private LocalDate creationDate;

    public User(String name, String surname, String email, String phone, boolean isActive) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.isActive = isActive;
        this.creationDate = LocalDate.now();
    }
}