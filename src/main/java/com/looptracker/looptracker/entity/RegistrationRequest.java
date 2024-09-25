package com.looptracker.looptracker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "registration_request", schema = "looptracker")
public class RegistrationRequest {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "firt_name", nullable = false)
    private String firtName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "license_number", nullable = false)
    private String licenseNumber;

    @Column(name = "citizenId_number", nullable = false)
    private String citizenidNumber;

    @Column(name = "status", nullable = false)
    private Long status;

    @Column(name = "create_at", nullable = false)
    private Instant createAt;

}