package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.request.TourPackageRequest;
import com.looptracker.looptracker.entity.DetailedItinerary;
import com.looptracker.looptracker.entity.TourPackage;
import com.looptracker.looptracker.mapper.DetailedItineraryMapper;
import com.looptracker.looptracker.mapper.TourPackageMapper;
import com.looptracker.looptracker.repository.IDetailedItineraryRepository;
import com.looptracker.looptracker.repository.ITourPackageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TourPackageService implements ITourPackageService {
    @Autowired
    ITourPackageRepository tourPackageRepository;
    @Autowired
    IDetailedItineraryRepository detailedItineraryRepository;
    @Autowired
    TourPackageMapper tourPackageMapper;
    @Autowired
    DetailedItineraryMapper detailedItineraryMapper;

    @Override
    @Transactional
    public void createTourPackage(TourPackageRequest tourPackageRequest) {
        LocalDateTime now = LocalDateTime.now();
        TourPackage tourPackage = tourPackageMapper.toEntity(tourPackageRequest.getTourPackage());
        tourPackage.setCreateAt(now);
        tourPackage.setUpdateAt(now);
        tourPackageRepository.saveAndFlush(tourPackage);

        List<DetailedItinerary> detailedItineraryList = detailedItineraryMapper.toListEntity(tourPackageRequest.getDetailedItinerary());
        for (DetailedItinerary detailedItinerary:detailedItineraryList) {
            detailedItinerary.setCreateAt(now);
            detailedItinerary.setUpdateAt(now);
            detailedItinerary.setTourPackage(tourPackage);
        }
        detailedItineraryRepository.saveAll(detailedItineraryList);
    }
}
