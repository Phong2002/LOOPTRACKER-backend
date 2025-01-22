package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.TourAssignmentDto;
import com.looptracker.looptracker.dto.request.TourAssignmentRequest;
import com.looptracker.looptracker.entity.*;
import com.looptracker.looptracker.entity.enums.DriverStatus;
import com.looptracker.looptracker.entity.enums.Role;
import com.looptracker.looptracker.entity.enums.TourInstanceStatus;
import com.looptracker.looptracker.exception.CustomException;
import com.looptracker.looptracker.exception.ErrorCode;
import com.looptracker.looptracker.mapper.TourAssignmentMapper;
import com.looptracker.looptracker.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TourAssignmentService implements ITourAssignmentService {

    @Autowired
    private ITourAssignmentRepository tourAssignmentRepository;

    @Autowired
    private ITourInstanceRepository tourInstanceRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private TourAssignmentMapper tourAssignmentMapper;

    @Autowired
    private IAssignmentItemService assignmentItemService;

    @Autowired
    private IPassengerRepository passengerRepository;

    @Autowired
    private IRiderInforRepository riderInforRepository;

    @Override
    @Transactional
    public void addTourAssignment(TourAssignmentRequest tourAssignmentRequest, Long tourInstanceId) {
        TourAssignment tourAssignment = new TourAssignment();
        if (tourInstanceId != null && tourInstanceId != 0) {
            TourInstance tourInstance = tourInstanceRepository.findById(tourInstanceId)
                    .orElseThrow(() -> new CustomException(ErrorCode.TOUR_INSTANCE_NOT_FOUND, "Can't find tour instance", HttpStatus.NOT_FOUND));
            tourAssignment.setTourInstances(tourInstance);
        }
        assignRider(tourAssignmentRequest, tourAssignment);
        assignPassenger(tourAssignmentRequest, tourAssignment);
        Optional.ofNullable(tourAssignmentRequest.getLicensePlates())
                .ifPresent(tourAssignment::setLicensePlates);
        tourAssignmentRepository.saveAndFlush(tourAssignment);
        assignItems(tourAssignmentRequest, tourAssignment);
    }

    @Override
    @Transactional
    public void updateTourAssignment(TourAssignmentRequest tourAssignmentRequest, Long tourInstanceId) {
        if(Objects.isNull(tourAssignmentRequest.getId())){
            addTourAssignment(tourAssignmentRequest, tourInstanceId);
        }
        else {
        TourAssignment tourAssignment = tourAssignmentRepository.findById(tourAssignmentRequest.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.TOUR_ASSIGNMENT_NOT_FOUND, tourAssignmentRequest.getId(), HttpStatus.NOT_FOUND));

        handleRiderUpdate(tourAssignmentRequest, tourAssignment);
        assignPassenger(tourAssignmentRequest, tourAssignment);
        Optional.ofNullable(tourAssignmentRequest.getLicensePlates())
                .ifPresent(tourAssignment::setLicensePlates);

        tourAssignmentRepository.saveAndFlush(tourAssignment);
        updateAssignItems(tourAssignmentRequest, tourAssignment);
        }

    }

    private void assignRider(TourAssignmentRequest tourAssignmentRequest, TourAssignment tourAssignment) {
        if (tourAssignmentRequest.getRider() != null && !tourAssignmentRequest.getRider().isEmpty()) {
            User rider = userRepository.findById(tourAssignmentRequest.getRider())
                    .orElseThrow(() -> new CustomException(ErrorCode.RIDER_NOT_FOUND, "Can't find rider", HttpStatus.NOT_FOUND));
            validateRider(rider);
            updateRiderStatus(rider, DriverStatus.ON_TRIP);
            tourAssignment.setRider(rider);
        }
    }

    private void handleRiderUpdate(TourAssignmentRequest tourAssignmentRequest, TourAssignment tourAssignment) {
        String newRiderId = tourAssignmentRequest.getRider();
        if ((newRiderId != null && !newRiderId.isEmpty())) {
            if (tourAssignment.getRider() != null) {
                if (!newRiderId.equals(tourAssignment.getRider().getId())) {
                    User newRider = userRepository.findById(newRiderId)
                            .orElseThrow(() -> new CustomException(ErrorCode.RIDER_NOT_FOUND, "Can't find rider", HttpStatus.NOT_FOUND));
                    validateRider(newRider);
                    updateRiderStatus(tourAssignment.getRider(), DriverStatus.READY);
                    updateRiderStatus(newRider, DriverStatus.ON_TRIP);
                    tourAssignment.setRider(newRider);
                }
            } else {
                assignRider(tourAssignmentRequest, tourAssignment);
            }
        } else if((newRiderId == null || newRiderId.isEmpty()) && tourAssignment.getRider() != null) {
            updateRiderStatus(tourAssignment.getRider(), DriverStatus.READY);
            tourAssignment.setRider(null);
            tourAssignmentRepository.save(tourAssignment);
        }
    }

    private void validateRider(User rider) {
        if (!(rider.getRole().equals(Role.TOUR_GUIDE) || rider.getRole().equals(Role.EASY_RIDER))) {
            throw new CustomException(ErrorCode.MUST_HAVE_ROLE_RIDER, rider.getLastName() + " " + rider.getFirstName(), HttpStatus.BAD_REQUEST);
        }
        if (rider.getRiderInfors().getRiderStatus().equals(DriverStatus.ON_TRIP)) {
            throw new CustomException(ErrorCode.RIDER_IN_OTHER_TOUR, rider.getLastName() + " " + rider.getFirstName(), HttpStatus.BAD_REQUEST);
        }
        if (rider.getRiderInfors().getRiderStatus().equals(DriverStatus.NOT_READY)) {
            throw new CustomException(ErrorCode.RIDER_IS_NOT_READY, rider.getLastName() + " " + rider.getFirstName(), HttpStatus.BAD_REQUEST);
        }
    }

    private void updateRiderStatus(User rider, DriverStatus status) {
        RiderInfor riderInfor = riderInforRepository.findByUser_Id(rider.getId());
        riderInfor.setRiderStatus(status);
        riderInforRepository.save(riderInfor);
    }

    private void assignPassenger(TourAssignmentRequest tourAssignmentRequest, TourAssignment tourAssignment) {
        Passenger passenger = passengerRepository.findById(tourAssignmentRequest.getPassenger()).orElseThrow(
                () -> new CustomException(ErrorCode.PASSENGER_NOT_FOUND, "Can't find passenger", HttpStatus.NOT_FOUND)
        );
        tourAssignment.setPassenger(passenger);
    }

    private void assignItems(TourAssignmentRequest tourAssignmentRequest, TourAssignment tourAssignment) {
        if (!tourAssignmentRequest.getListItems().isEmpty()) {
            tourAssignmentRequest.getListItems().forEach(item -> assignmentItemService.assignmentItemTo(item, tourAssignment.getId()));
        }
    }

    private void updateAssignItems(TourAssignmentRequest tourAssignmentRequest, TourAssignment tourAssignment) {
        if (!tourAssignmentRequest.getListItems().isEmpty()) {
            tourAssignmentRequest.getListItems().forEach(item -> assignmentItemService.updateAssignmentItem(item, tourAssignment.getId()));
        }
    }

    @Override
    @Transactional
    public void deleteTourAssignment(String id) {
        TourAssignment tourAssignment = tourAssignmentRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.TOUR_PACKAGE_NOT_FOUND, "Can't find tour assignment", HttpStatus.NOT_FOUND));
        if (tourAssignment.getTourInstances().getStatus().equals(TourInstanceStatus.IN_PROGRESS) ||
                tourAssignment.getTourInstances().getStatus().equals(TourInstanceStatus.COMPLETED)) {
            throw new CustomException(ErrorCode.CAN_NOT_DELETE, "Can't delete", HttpStatus.BAD_REQUEST);
        }
        if (tourAssignment.getRider() != null) {
            updateRiderStatus(tourAssignment.getRider(), DriverStatus.READY);
        }
        tourAssignmentRepository.delete(tourAssignment);
    }

    @Override
    public void deleteAllTourAssignments(List<String> ids) {
        for (String id : ids) {
            deleteTourAssignment(id);
        }
    }

    @Override
    public Page<TourAssignmentDto> getAllTourAssignments(Pageable pageable) {
        Page<TourAssignment> tourAssignments = tourAssignmentRepository.findAll(pageable);
        return tourAssignmentMapper.toPageDto(tourAssignments);
    }

    @Override
    public Page<TourAssignmentDto> getAllTourAssignmentsByTourInstance(Pageable pageable, Long tourInstanceId) {
        Page<TourAssignment> tourAssignments = tourAssignmentRepository.findAllByTourInstances_Id(tourInstanceId, pageable);
        return tourAssignmentMapper.toPageDto(tourAssignments);
    }

    @Override
    public TourAssignmentDto getById(String id) {
        return tourAssignmentMapper.toDto(tourAssignmentRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.TOUR_ASSIGNMENT_NOT_FOUND, "Tour assignment not found", HttpStatus.NOT_FOUND)
        ));
    }
}
