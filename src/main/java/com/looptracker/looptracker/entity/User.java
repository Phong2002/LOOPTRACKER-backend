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

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "phoneNumber", length = 20)
    private String phoneNumber;

    @Lob
    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Lob
    @Column(name = "role", nullable = false)
    private String role;

}