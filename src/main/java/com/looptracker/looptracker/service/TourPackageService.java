package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.TourPackageDto;
import com.looptracker.looptracker.dto.request.TourPackageRequest;
import com.looptracker.looptracker.entity.DetailedItinerary;
import com.looptracker.looptracker.entity.TourPackage;
import com.looptracker.looptracker.exception.CustomException;
import com.looptracker.looptracker.mapper.DetailedItineraryMapper;
import com.looptracker.looptracker.mapper.TourPackageMapper;
import com.looptracker.looptracker.repository.IDetailedItineraryRepository;
import com.looptracker.looptracker.repository.ITourPackageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TourPackageService implements ITourPackageService {
    @Autowired
    private ITourPackageRepository tourPackageRepository;
    @Autowired
    private IDetailedItineraryRepository detailedItineraryRepository;
    @Autowired
    private TourPackageMapper tourPackageMapper;
    @Autowired
    private DetailedItineraryMapper detailedItineraryMapper;

    @Override
    @Transactional
    public void createTourPackage(TourPackageRequest tourPackageRequest) {
        if(tourPackageRepository.existsById(tourPackageRequest.getTourPackage().getId())){
            throw new CustomException(400, "TourPackage already exists");
        }

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

    @Override
    public Page<TourPackageDto> getAllTourPackages(Pageable pageable) {
        Page<TourPackage> tourPackages = tourPackageRepository.findAll(pageable);
        Page<TourPackageDto> tourPackageDtos = tourPackageMapper.toPageDto(tourPackages);
        return tourPackageDtos;
    }

    @Override
    public TourPackageDto getTourPackage(String id) {
        TourPackage tourPackage = tourPackageRepository.findById(id).orElseThrow(
                ()->  new CustomException(404, "tourPackage Not Found")
        );
        TourPackageDto tourPackageDto = tourPackageMapper.toDto(tourPackage);
        return tourPackageDto;
    }

    @Override
    @Transactional
    public void updateTourPackage(TourPackageDto tourPackageDto) {

    }

    @Override
    @Transactional
    public void deleteTourPackage(String id) {
        tourPackageRepository.deleteById(id);
    }
}
