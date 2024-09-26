package com.looptracker.looptracker.dto.request;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TourInstanceRequest {
    private String tourPackage;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String tourGuide;
}
