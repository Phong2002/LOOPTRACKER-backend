package com.looptracker.looptracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rider_infor", schema = "looptracker")
public class RiderInfor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "license_number", length = 50)
    private String licenseNumber;

    @Column(name = "citizenId_number", length = 50)
    private String citizenidNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}