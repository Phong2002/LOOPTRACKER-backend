package com.looptracker.looptracker.dto;

import com.looptracker.looptracker.entity.AssignmentItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder
public class TourAssignmentDto {
    private String id;
//    private TourInstanceDto tourInstances;
    private PassengerDto passenger;
    private UserDto rider;
    private String licensePlates;
    private List<AssignmentItemDto> assignmentItems ;

}
