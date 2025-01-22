package com.looptracker.looptracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "incident", schema = "looptracker")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 50)
    @NotNull
    @Column(name = "incident_type", nullable = false, length = 50)
    private String incidentType;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "damage_cost", precision = 18, scale = 2)
    private BigDecimal damageCost;

    @Size(max = 255)
    @Column(name = "latitude")
    private String latitude;

    @Size(max = 255)
    @Column(name = "longitude")
    private String longitude;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "incident_date")
    private LocalDateTime incidentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by",referencedColumnName = "id")
    @CreatedBy
    private User createdBy;

    @Column(name = "tour_assignments")
    private String tourAssignments;

    @Size(max = 50)
    @Column(name = "involved_role", length = 50)
    private String involvedRole;

    @OneToMany(mappedBy = "incident")
    @JsonManagedReference
    private List<IncidentEvidence> incidentEvidences;

}