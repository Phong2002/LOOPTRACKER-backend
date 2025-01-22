package com.looptracker.looptracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.looptracker.looptracker.entity.enums.ItemMovementType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "item_movements", schema = "looptracker")
public class ItemMovement {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item", nullable = false)
    @JsonIgnore
    private Item item;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemMovementType type;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

}