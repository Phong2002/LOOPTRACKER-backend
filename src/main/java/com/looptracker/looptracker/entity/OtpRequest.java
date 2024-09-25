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
@Table(name = "otp_request", schema = "looptracker")
public class OtpRequest {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "otp", nullable = false)
    private String otp;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "create_at", nullable = false)
    private Instant createAt;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed = false;

}