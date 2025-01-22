package com.looptracker.looptracker.dto.request;

import lombok.Data;

@Data
public class TourInstanceUpdateRequest {
    private TourInstanceRequest tourInstanceRequest;
    private ListIdInTourPackage listIdInTourPackage;
}
