package com.looptracker.looptracker.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class TourPackageUpdateRequest extends TourPackageRequest {
    private List<Integer> listIdDelete;
}
