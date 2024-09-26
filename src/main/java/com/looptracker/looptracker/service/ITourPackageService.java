package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.TourPackageDto;
import com.looptracker.looptracker.dto.request.TourPackageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITourPackageService {
    void createTourPackage(TourPackageRequest tourPackageRequest);
    Page<TourPackageDto> getAllTourPackages(Pageable pageable);
    TourPackageDto getTourPackage(String id);
    void updateTourPackage(TourPackageDto tourPackageDto);
    void deleteTourPackage(String id);
}
