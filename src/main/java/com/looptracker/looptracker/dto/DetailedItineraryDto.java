package com.looptracker.looptracker.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder
public class DetailedItineraryDto {
    private Integer id;
    private String from;
    private String to;
    private Integer day;
    private String description;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private List<WayPointDto> wayPoints;
}
