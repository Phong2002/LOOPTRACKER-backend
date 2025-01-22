package com.looptracker.looptracker.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.looptracker.looptracker.entity.enums.DriverStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @Lob
    @Column(name = "cccd_front")
    private String cccdFront;

    @Lob
    @Column(name = "cccd_back")
    private String cccdBack;

    @Lob
    @Column(name = "gplx_front")
    private String gplxFront;

    @Lob
    @Column(name = "gplx_back")
    private String gplxBack;

}