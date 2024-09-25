package com.looptracker.looptracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "looptracker")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

}