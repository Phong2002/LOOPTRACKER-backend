package com.looptracker.looptracker.dto.request;

import com.looptracker.looptracker.dto.DetailedItineraryDto;
import com.looptracker.looptracker.dto.TourPackageDto;
import lombok.Data;

import java.util.List;

@Data
public class TourPackageRequest {
    private TourPackageDto tourPackage;
    private List<DetailedItineraryDto> detailedItinerary;
}
