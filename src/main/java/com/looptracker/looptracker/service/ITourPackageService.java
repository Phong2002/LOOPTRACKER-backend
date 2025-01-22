package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.TourPackageDto;
import com.looptracker.looptracker.dto.request.TourPackageRequest;
import com.looptracker.looptracker.dto.request.TourPackageUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITourPackageService {
    void createTourPackage(TourPackageRequest tourPackageRequest);
    Page<TourPackageDto> getAllTourPackages(String search,Integer day,Long minPrice,Long maxPrice,Pageable pageable);
    TourPackageDto getTourPackage(String id);
    void updateTourPackage(TourPackageUpdateRequest tourPackageUpdateRequest);
    void deleteTourPackage(String id);
    public TourPackageDto getTourPackageById(String id);
}
