package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.TourPackageDto;
import com.looptracker.looptracker.dto.request.TourPackageRequest;
import com.looptracker.looptracker.dto.request.TourPackageUpdateRequest;
import com.looptracker.looptracker.entity.DetailedItinerary;
import com.looptracker.looptracker.entity.TourPackage;
import com.looptracker.looptracker.entity.WayPoint;
import com.looptracker.looptracker.exception.CustomException;
import com.looptracker.looptracker.exception.ErrorCode;
import com.looptracker.looptracker.mapper.DetailedItineraryMapper;
import com.looptracker.looptracker.mapper.TourPackageMapper;
import com.looptracker.looptracker.mapper.WayPointMapper;
import com.looptracker.looptracker.repository.IDetailedItineraryRepository;
import com.looptracker.looptracker.repository.ITourPackageRepository;
import com.looptracker.looptracker.repository.IWayPointRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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
    @Autowired
    private IWayPointRepository wayPointRepository;
    @Autowired
    private WayPointMapper wayPointMapper;

    @Override
    @Transactional
    public void createTourPackage(TourPackageRequest tourPackageRequest) {
        // Kiểm tra nếu TourPackage đã tồn tại
        if (tourPackageRepository.existsById(tourPackageRequest.getTourPackage().getId())) {
            throw new CustomException(
                    ErrorCode.TOUR_PACKAGE_ALREADY_EXISTS,
                    "TourPackage already exists",
                    HttpStatus.BAD_REQUEST
            );
        }

        // Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();

        // Map TourPackage từ DTO sang entity và lưu
        TourPackage tourPackage = tourPackageMapper.toEntity(tourPackageRequest.getTourPackage());
        tourPackage.setCreateAt(now);
        tourPackage.setUpdateAt(now);
        tourPackage = tourPackageRepository.save(tourPackage); // Chỉ cần gọi save thay vì saveAndFlush

        // Map danh sách DetailedItinerary và thiết lập TourPackage
        List<DetailedItinerary> detailedItineraryList =
                detailedItineraryMapper.toListEntity(tourPackageRequest.getDetailedItinerary());

        detailedItineraryList.forEach(itinerary -> {
            itinerary.setCreateAt(LocalDateTime.now());
            itinerary.setUpdateAt(LocalDateTime.now());
            itinerary.setTourPackage(tourPackageMapper.toEntity(tourPackageRequest.getTourPackage()));
        });


        // Lưu toàn bộ DetailedItinerary
        detailedItineraryList = detailedItineraryRepository.saveAll(detailedItineraryList);

        // Lưu toàn bộ WayPoint
        List<WayPoint> wayPoints = new ArrayList<>();
        for (DetailedItinerary itinerary : detailedItineraryList) {
            List<WayPoint> itineraryWayPoints = itinerary.getWayPoints();
            itineraryWayPoints.forEach(wayPoint -> wayPoint.setDetailedItinerary(itinerary));
            wayPoints.addAll(itineraryWayPoints);
        }

        if (!wayPoints.isEmpty()) {
            wayPointRepository.saveAll(wayPoints); // Lưu toàn bộ danh sách WayPoint một lần
        }
    }

    @Override
    public Page<TourPackageDto> getAllTourPackages(String search, Integer day, Long minPrice, Long maxPrice,Pageable pageable) {
        Page<TourPackage> tourPackages = tourPackageRepository.findAllAndFilter(search,day,minPrice,maxPrice,pageable);
        if (tourPackages != null) {
            tourPackages.forEach(tourPackage -> {
                if (tourPackage.getDetailedItineraries() != null) {
                    tourPackage.getDetailedItineraries().forEach(detailedItinerary -> {
                        if (detailedItinerary.getWayPoints() != null) {
                            detailedItinerary.getWayPoints().sort(Comparator.comparingInt(WayPoint::getIndex));
                        }
                    });
                }
            });
        }
        Page<TourPackageDto> tourPackageDtos = tourPackageMapper.toPageDto(tourPackages);
        return tourPackageDtos;
    }

    @Override
    public TourPackageDto getTourPackageById(String id) {
        TourPackage tourPackage = tourPackageRepository.findById(id).get();
                if (tourPackage.getDetailedItineraries() != null) {
                    tourPackage.getDetailedItineraries().forEach(detailedItinerary -> {
                        if (detailedItinerary.getWayPoints() != null) {
                            detailedItinerary.getWayPoints().sort(Comparator.comparingInt(WayPoint::getIndex));
                        }
                    });
                }
        return tourPackageMapper.toDto(tourPackage);
    }

    @Override
    public TourPackageDto getTourPackage(String id) {
        TourPackage tourPackage = tourPackageRepository.findById(id).orElseThrow(
                ()->  new CustomException(ErrorCode.TOUR_PACKAGE_NOT_FOUND, "tourPackage Not Found",HttpStatus.NOT_FOUND)
        );
        TourPackageDto tourPackageDto = tourPackageMapper.toDto(tourPackage);
        return tourPackageDto;
    }

    @Override
    @Transactional
    public void updateTourPackage(TourPackageUpdateRequest tourPackageRequest) {
        LocalDateTime now = LocalDateTime.now();

        // Map và lưu TourPackage
        TourPackage tourPackage = tourPackageMapper.toEntity(tourPackageRequest.getTourPackage());
        tourPackage.setCreateAt(now);
        tourPackage.setUpdateAt(now);
        tourPackageRepository.saveAndFlush(tourPackage);

        // Map danh sách DetailedItinerary
        List<DetailedItinerary> detailedItineraries = detailedItineraryMapper.toListEntity(tourPackageRequest.getDetailedItinerary());

        // Gán thông tin và lưu danh sách DetailedItinerary
        detailedItineraries.forEach(itinerary -> {
            if (itinerary.getId() == null) {
                itinerary.setCreateAt(now);
            }
            itinerary.setUpdateAt(now);
            itinerary.setTourPackage(tourPackage);

            // Gán thông tin cho WayPoints
            itinerary.getWayPoints().forEach(wayPoint -> wayPoint.setDetailedItinerary(itinerary));
        });

        // Lưu tất cả DetailedItinerary và WayPoints
        detailedItineraryRepository.saveAll(detailedItineraries);

        // Xóa DetailedItinerary theo danh sách ID (nếu có)
        if (Objects.nonNull(tourPackageRequest.getListIdDelete()) && !tourPackageRequest.getListIdDelete().isEmpty()) {
            detailedItineraryRepository.deleteAllById(tourPackageRequest.getListIdDelete());
        }
    }

    @Override
    @Transactional
    public void deleteTourPackage(String id) {
        tourPackageRepository.deleteById(id);
    }
}
