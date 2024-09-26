package com.looptracker.looptracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tour_packages", schema = "looptracker")
public class TourPackage {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Lob
    @Column(name = "tour_name", nullable = false)
    private String tourName;

    @Column(name = "day", nullable = false)
    private Byte day;

    @Column(name = "night", nullable = false)
    private Byte night;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private Long price;

}