package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.TourAssignmentDto;
import com.looptracker.looptracker.dto.request.TourAssignmentRequest;
import com.looptracker.looptracker.entity.Passenger;
import com.looptracker.looptracker.entity.TourAssignment;
import com.looptracker.looptracker.entity.TourInstance;
import com.looptracker.looptracker.entity.User;
import com.looptracker.looptracker.entity.enums.DriverStatus;
import com.looptracker.looptracker.entity.enums.Role;
import com.looptracker.looptracker.entity.enums.TourInstanceStatus;
import com.looptracker.looptracker.exception.CustomException;
import com.looptracker.looptracker.mapper.TourAssignmentMapper;
import com.looptracker.looptracker.repository.IPassengerRepository;
import com.looptracker.looptracker.repository.ITourAssignmentRepository;
import com.looptracker.looptracker.repository.ITourInstanceRepository;
import com.looptracker.looptracker.repository.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class TourAssignmentService implements ITourAssignmentService {

    @Autowired
    private ITourAssignmentRepository tourAssignmentRepository;

    @Autowired
    private ITourInstanceRepository tourInstanceRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IPassengerRepository passengerRepository;

    @Autowired
    private TourAssignmentMapper tourAssignmentMapper;

    @Override
    @Transactional
    public void addTourAssignment(TourAssignmentRequest tourAssignmentRequest) {
        TourAssignment tourAssignment = new TourAssignment();
        if(tourAssignmentRequest.getTourInstances()!=null&&tourAssignmentRequest.getTourInstances().intValue()!=0){
            TourInstance tourInstance = tourInstanceRepository.findById(tourAssignmentRequest.getTourInstances())
                    .orElseThrow(()->new CustomException(404,"Can't found tour instance"));
            tourAssignment.setTourInstances(tourInstance);
        }
        if(tourAssignmentRequest.getRider()!=null&& !tourAssignmentRequest.getRider().isEmpty()){
            User rider = userRepository.findById(tourAssignmentRequest.getRider())
                    .orElseThrow(()-> new CustomException(404,"Can't found rider"));
            if(!(rider.getRole().equals(Role.TOUR_GUIDE)||rider.getRole().equals(Role.EASY_RIDER))){
                throw new CustomException(400,"Rider must has role EASY_RIDE or TOUR_GUIDE");
            }
            if(rider.getRiderInfors().getRiderStatus().equals(DriverStatus.ON_TRIP)){
                throw new CustomException(400,"Rider is in other tour");
            }
            if(rider.getRiderInfors().getRiderStatus().equals(DriverStatus.NOT_READY)){
                throw new CustomException(400,"Rider is not ready now");
            }
            tourAssignment.setRider(rider);
        }
        if(tourAssignmentRequest.getLicensePlates()!=null){
            tourAssignment.setLicensePlates(tourAssignment.getLicensePlates());
        }
        tourAssignmentRepository.save(tourAssignment);
    }

    @Override
    @Transactional
    public void updateTourAssignment(TourAssignmentRequest tourAssignmentRequest) {
        TourAssignment tourAssignment = tourAssignmentRepository.findById(tourAssignmentRequest.getId()).orElseThrow(
                ()-> new CustomException(404,"Can't found tour assignment"));

        TourInstance tourInstance = tourInstanceRepository.findById(tourAssignmentRequest.getTourInstances()).orElseThrow(
                ()->new CustomException(404,"Can't fount tour instance")
        );

        tourAssignment.setTourInstances(tourInstance);


        if (tourAssignment.getRider() == null ||
                !Objects.equals(tourAssignment.getRider().getId(), tourAssignmentRequest.getRider())) {
            if (tourAssignmentRequest.getRider().isEmpty()) {
                tourAssignment.setRider(null);
            }
            User rider = userRepository.findById(tourAssignmentRequest.getRider())
                    .orElseThrow(() -> new CustomException(404, "Can't find rider"));
            if (!(rider.getRole().equals(Role.TOUR_GUIDE) || rider.getRole().equals(Role.EASY_RIDER))) {
                throw new CustomException(400, "Rider must have role EASY_RIDER or TOUR_GUIDE");
            }
            DriverStatus riderStatus = rider.getRiderInfors().getRiderStatus();
            if (riderStatus == DriverStatus.ON_TRIP) {
                throw new CustomException(400, "Rider is already on another tour");
            }
            if (riderStatus == DriverStatus.NOT_READY) {
                throw new CustomException(400, "Rider is not ready at the moment");
            }
            tourAssignment.setRider(rider);
        }



        tourAssignment.setLicensePlates(tourAssignmentRequest.getLicensePlates());
        tourAssignmentRepository.save(tourAssignment);
    }

    @Override
    @Transactional
    public void deleteTourAssignment(String id) {
        TourAssignment tourAssignment = tourAssignmentRepository.findById(id).orElseThrow(
                ()-> new CustomException(404,"Can't fount tour assignment"));
        if(tourAssignment.getTourInstances().getStatus().equals(TourInstanceStatus.IN_PROGRESS)||
                tourAssignment.getTourInstances().getStatus().equals(TourInstanceStatus.COMPLETED)){
            throw new CustomException(400,"Can't delete ");
        }
        tourAssignmentRepository.delete(tourAssignment);
    }

    @Override
    public Page<TourAssignmentDto> getAllTourAssignments(Pageable pageable) {
        Page<TourAssignment> tourAssignments = tourAssignmentRepository.findAll(pageable);
        Page<TourAssignmentDto> tourAssignmentDtos =  tourAssignmentMapper.toPageDto(tourAssignments);
        return tourAssignmentDtos;
    }

    @Override
    public Page<TourAssignmentDto> getAllTourAssignmentsByTourInstance(Pageable pageable,Long tourInstanceId) {
        Page<TourAssignment> tourAssignments = tourAssignmentRepository.findAllByTourInstances_Id(tourInstanceId,pageable);
        Page<TourAssignmentDto> tourAssignmentDtos =  tourAssignmentMapper.toPageDto(tourAssignments);
        return tourAssignmentDtos;
    }
}
