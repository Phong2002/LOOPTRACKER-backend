package com.looptracker.looptracker.dto;

import com.looptracker.looptracker.entity.TourPackage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder
public class DetailedItineraryDto {
    private Integer id;
    private TourPackage tourPackage;
    private Integer day;
    private String description;
    private Instant createAt;
    private Instant updateAt;
}
