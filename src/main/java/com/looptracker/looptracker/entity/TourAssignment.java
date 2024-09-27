package com.looptracker.looptracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tour_assignments", schema = "looptracker")
public class TourAssignment {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_instances")
    private TourInstance tourInstances;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger")
    private Passenger passenger;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "easy_rider")
    private User easyRider;

    @Column(name = "license_plates")
    private String licensePlates;

}