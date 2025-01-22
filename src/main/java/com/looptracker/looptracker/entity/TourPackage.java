package com.looptracker.looptracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tour_packages", schema = "looptracker")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TourPackage extends BaseEntity{
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "tour_name", nullable = false)
    private String tourName;

    @Column(name = "day", nullable = false)
    private Integer day;

    @Column(name = "night", nullable = false)
    private Integer night;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private Long price;

    @OneToMany(mappedBy = "tourPackage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<DetailedItinerary> detailedItineraries = new ArrayList<>();

}