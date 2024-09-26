package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.TourPackageDto;
import com.looptracker.looptracker.dto.request.TourPackageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TourInstanceService implements ITourPackageService{
    @Override
    public void createTourPackage(TourPackageRequest tourPackageRequest) {

    }

    @Override
    public Page<TourPackageDto> getAllTourPackages(Pageable pageable) {
        return null;
    }

    @Override
    public TourPackageDto getTourPackage(String id) {
        return null;
    }

    @Override
    public void updateTourPackage(TourPackageDto tourPackageDto) {

    }

    @Override
    public void deleteTourPackage(String id) {

    }
}
