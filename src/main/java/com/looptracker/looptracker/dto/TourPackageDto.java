package com.looptracker.looptracker.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder
public class TourPackageDto {
    private String id;
    private String tourName;
    private Integer day;
    private Integer night;
    private String description;
    private Long price;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
