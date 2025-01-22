package com.looptracker.looptracker.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "way_points", schema = "looptracker")
public class WayPoint {
    @Id
    @Size(max = 255)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Size(max = 255)
    @Column(name = "lat")
    private String lat;

    @Size(max = 255)
    @Column(name = "lon")
    private String lon;

    @Size(max = 255)
    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @Size(max = 255)
    @Column(name = "location")
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "detailed_itinerary")
    @JsonBackReference
    private DetailedItinerary detailedItinerary;

    @NotNull
    @Column(name = "`index`", nullable = false)
    private Integer index;

}