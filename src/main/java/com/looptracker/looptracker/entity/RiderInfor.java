package com.looptracker.looptracker.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.looptracker.looptracker.entity.enums.DriverStatus;
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

    @Column(name = "citizen_id_number", length = 50)
    private String citizenIdNumber;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;

    @Column(name = "rider_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DriverStatus riderStatus;

}