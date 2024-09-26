package com.looptracker.looptracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "otp_request", schema = "looptracker")
public class OtpRequest {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "otp", nullable = false)
    private String otp;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;


}