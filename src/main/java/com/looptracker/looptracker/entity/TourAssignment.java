package com.looptracker.looptracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "easy_rider")
    private User rider;

    @Column(name = "license_plates")
    private String licensePlates;

    @OneToMany(mappedBy = "tourAssignments")
    private List<AssignmentItem> assignmentItems ;

}