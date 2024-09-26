package com.looptracker.looptracker.dto;

import com.looptracker.looptracker.entity.TourPackage;
import com.looptracker.looptracker.entity.User;
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
public class TourInstanceDto {
    private Long id;
    private TourPackage tourPackage;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private User tourGuide;
}
