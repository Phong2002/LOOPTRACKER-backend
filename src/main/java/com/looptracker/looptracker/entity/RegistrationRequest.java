package com.looptracker.looptracker.entity;

import com.looptracker.looptracker.entity.enums.RegistrationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "registration_request", schema = "looptracker")
public class RegistrationRequest {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

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

    @Column(name = "citizen_id_number", nullable = false)
    private String citizenIdNumber;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RegistrationStatus status;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

}