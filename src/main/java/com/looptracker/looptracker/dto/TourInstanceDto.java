package com.looptracker.looptracker.dto;

import com.looptracker.looptracker.entity.TourAssignment;
import com.looptracker.looptracker.entity.TourPackage;
import com.looptracker.looptracker.entity.enums.TourInstanceStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder
public class TourInstanceDto {
    private Long id;
    private String name;
    private TourPackage tourPackage;
    private LocalDate startDate;
    private LocalDate endDate;
    private UserDto tourGuide;
    private TourInstanceStatus status;
    private List<TourAssignmentDto> tourAssignments ;
}
