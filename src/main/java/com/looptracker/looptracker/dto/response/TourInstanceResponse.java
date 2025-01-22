package com.looptracker.looptracker.dto.response;

import com.looptracker.looptracker.entity.enums.TourInstanceStatus;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder
public class TourInstanceResponse {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer day;
    private Integer night;
    private String name;
    private String status;
    private String tourPackageId;
    private String tourPackageName;
    private String tourGuideFirstName;
    private String tourGuideLastName;
    private String tourGuideId;
    private Long totalRider;
    private Long totalPassenger;

}