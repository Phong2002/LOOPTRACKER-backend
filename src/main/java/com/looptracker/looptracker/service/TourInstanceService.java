package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.TourInstanceDto;
import com.looptracker.looptracker.dto.request.TourInstanceRequest;
import com.looptracker.looptracker.entity.TourInstance;
import com.looptracker.looptracker.entity.TourPackage;
import com.looptracker.looptracker.entity.User;
import com.looptracker.looptracker.entity.enums.Role;
import com.looptracker.looptracker.exception.CustomException;
import com.looptracker.looptracker.mapper.TourInstanceMapper;
import com.looptracker.looptracker.repository.IRiderInforRepository;
import com.looptracker.looptracker.repository.ITourInstanceRepository;
import com.looptracker.looptracker.repository.ITourPackageRepository;
import com.looptracker.looptracker.repository.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TourInstanceService implements ITourInstanceService {
    @Autowired
    private ITourInstanceRepository tourInstanceRepository;
    @Autowired
    private TourInstanceMapper tourInstanceMapper;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ITourPackageRepository tourPackageRepository;

    @Override
    @Transactional
    public void createTourInstance(TourInstanceRequest tourInstanceRequest) {
        TourInstance tourInstance = new TourInstance();

        if (tourInstanceRequest.getTourGuide() != null && !tourInstanceRequest.getTourGuide().equals("")) {
            if (!userRepository.existsById(tourInstanceRequest.getTourGuide())) {
                throw new CustomException(404, "Can't found tour guide");
            }
            if (!userRepository.findById(tourInstanceRequest.getTourGuide()).get().getRole().equals(Role.TOUR_GUIDE)) {
                throw new CustomException(400, "Must have role tour guide");
            }
            User user = new User();
            user.setId(tourInstanceRequest.getTourGuide());
            tourInstance.setTourGuide(user);
        }

        if (!tourPackageRepository.existsById(tourInstanceRequest.getTourPackage())) {
            throw new CustomException(404, "Can't found tour package");
        }

        TourPackage tourPackage = new TourPackage();
        tourPackage.setId(tourInstanceRequest.getTourPackage());

        tourInstance.setTourPackage(tourPackage);
        tourInstance.setStartDate(tourInstanceRequest.getStartDate());
        tourInstance.setEndDate(tourInstanceRequest.getEndDate());
        tourInstanceRepository.save(tourInstance);
    }

    @Override
    @Transactional
    public void updateTourInstance(TourInstanceRequest tourInstanceRequest) {
        TourInstance tourInstance = tourInstanceRepository.findById(tourInstanceRequest.getId()).orElseThrow(
                () -> new CustomException(404, "Can't found tour instance")
        );

        User user = userRepository.findById(tourInstanceRequest.getTourGuide()).orElseThrow(
                () -> new CustomException(404, "Can't found tour guide"));

        if (!user.getRole().equals(Role.TOUR_GUIDE)) {
            throw new CustomException(400, "Must have role tour guide");
        }

        if (tourInstance.getTourGuide() == null ||
                !tourInstance.getTourGuide().getId().equals(tourInstanceRequest.getTourGuide())) {
            tourInstance.setTourGuide(user);
        }


        if (!tourInstance.getTourPackage().getId().equals(tourInstanceRequest.getTourPackage())) {
            TourPackage tourPackage = tourPackageRepository.findById(tourInstanceRequest.getTourPackage()).orElseThrow(
                    () -> new CustomException(404, "Can't found tour package"));
            tourInstance.setTourPackage(tourPackage);
        }

        tourInstance.setStartDate(tourInstanceRequest.getStartDate());
        tourInstance.setEndDate(tourInstanceRequest.getEndDate());
        tourInstanceRepository.save(tourInstance);

    }

    @Override
    @Transactional
    public void deleteTourInstance(Long tourInstanceId) {
        tourInstanceRepository.deleteById(tourInstanceId);
    }

    @Override
    public Page<TourInstanceDto> getAllTourInstances(Pageable pageable) {
        Page<TourInstance> tourInstances = tourInstanceRepository.findAll(pageable);
        Page<TourInstanceDto> tourInstanceDtos = tourInstanceMapper.toPageDto(tourInstances);
        return tourInstanceDtos;
    }
}
