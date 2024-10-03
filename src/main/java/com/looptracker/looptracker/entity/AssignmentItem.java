package com.looptracker.looptracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "assignment_items", schema = "looptracker")
public class AssignmentItem {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tour_assignments", nullable = false)
    private TourAssignment tourAssignments;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item", nullable = false)
    private Item item;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "status", nullable = false)
    private String status;

}