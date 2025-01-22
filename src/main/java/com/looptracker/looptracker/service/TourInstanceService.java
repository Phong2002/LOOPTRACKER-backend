package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.TourInstanceDto;
import com.looptracker.looptracker.dto.request.ListIdInTourPackage;
import com.looptracker.looptracker.dto.request.TourInstanceRequest;
import com.looptracker.looptracker.dto.response.AssignmentItemNoReturned;
import com.looptracker.looptracker.dto.response.ConfirmTripCompleteResponse;
import com.looptracker.looptracker.dto.response.TourInstanceResponse;
import com.looptracker.looptracker.entity.AssignmentItem;
import com.looptracker.looptracker.entity.TourInstance;
import com.looptracker.looptracker.entity.TourPackage;
import com.looptracker.looptracker.entity.User;
import com.looptracker.looptracker.entity.enums.Role;
import com.looptracker.looptracker.entity.enums.TourInstanceStatus;
import com.looptracker.looptracker.exception.CustomException;
import com.looptracker.looptracker.exception.ErrorCode;
import com.looptracker.looptracker.mapper.TourInstanceMapper;
import com.looptracker.looptracker.repository.*;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    @Autowired
    private ITourAssignmentService tourAssignmentService;
    @Autowired
    private IAssignmentItemService assignmentItemService;
    @Autowired
    private AssignmentItemRepository assignmentItemRepository;

    @Override
    @Transactional
    public void createTourInstance(TourInstanceRequest tourInstanceRequest) {
        TourInstance tourInstance = new TourInstance();

        setTourGuideIfPresent(tourInstance, tourInstanceRequest.getTourGuide());
        setTourPackage(tourInstance, tourInstanceRequest.getTourPackage());

        tourInstance.setStartDate(tourInstanceRequest.getStartDate());
        tourInstance.setEndDate(tourInstanceRequest.getEndDate());
        tourInstance.setName(tourInstanceRequest.getName());
        tourInstance.setStatus(TourInstanceStatus.PREPARING);
        tourInstance.setIsComplete(false);

        tourInstanceRepository.saveAndFlush(tourInstance);

        if (!tourInstanceRequest.getTourAssignments().isEmpty()) {
            tourInstanceRequest.getTourAssignments().forEach(assignment ->
                    tourAssignmentService.addTourAssignment(assignment, tourInstance.getId()));
        }
    }

    @Override
    @Transactional
    public void updateTourInstance(TourInstanceRequest tourInstanceRequest, ListIdInTourPackage listIdInTourPackage) {
        TourInstance tourInstance = tourInstanceRepository.findById(tourInstanceRequest.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.TOUR_INSTANCE_NOT_FOUND,
                        "Can't found tour instance", HttpStatus.NOT_FOUND));

        tourInstance.setName(tourInstanceRequest.getName());
        updateTourGuide(tourInstance, tourInstanceRequest.getTourGuide());
        updateTourPackage(tourInstance, tourInstanceRequest.getTourPackage());
        tourInstance.setStartDate(tourInstanceRequest.getStartDate());
        tourInstance.setEndDate(tourInstanceRequest.getEndDate());
        tourInstanceRepository.save(tourInstance);

        if(listIdInTourPackage.getTourAssignmentIds().size()>0){
            tourAssignmentService.deleteAllTourAssignments(listIdInTourPackage.getTourAssignmentIds());
        }
        if(listIdInTourPackage.getAssignmentItemIds().size()>0){
            assignmentItemService.deleteListAssignmentItem(listIdInTourPackage.getAssignmentItemIds());
        }

        if (!tourInstanceRequest.getTourAssignments().isEmpty()) {
            tourInstanceRequest.getTourAssignments().forEach(assignment ->
                    tourAssignmentService.updateTourAssignment(assignment, tourInstance.getId()));
        }
    }

    @Override
    @Transactional
    public void deleteTourInstance(Long tourInstanceId) {
        tourInstanceRepository.deleteById(tourInstanceId);
    }

    @Override
    public Page<TourInstanceResponse> getAllTourInstances(String search,String status,String tourPackage,Pageable pageable) {
        Page<Tuple> tourInstancesPage = tourInstanceRepository.getAllTourInstances(search,status,tourPackage,pageable);

        List<TourInstanceResponse> tourInstanceResponse = tourInstancesPage.stream()
                .map(tuple -> new TourInstanceResponse()
                        .setId(tuple.get("tour_instance_id", Long.class))
                        .setName(tuple.get("name",String.class))
                        .setStartDate(tuple.get("start_date", Date.class).toLocalDate())
                        .setEndDate(tuple.get("end_date", Date.class).toLocalDate())
                        .setStatus(tuple.get("status",String.class))
                        .setTourPackageId(tuple.get("tour_package_id",String.class))
                        .setTourPackageName(tuple.get("tour_name",String.class))
                        .setDay(tuple.get("day",Integer.class))
                        .setNight(tuple.get("night",Integer.class))
                        .setTourGuideId(tuple.get("guide_id",String.class))
                        .setTourGuideFirstName(tuple.get("guide_first_name",String.class))
                        .setTourGuideLastName(tuple.get("guide_last_name",String.class))
                        .setTotalRider(tuple.get("total_rider", BigDecimal.class).longValue())
                        .setTotalPassenger(tuple.get("total_passenger",BigDecimal.class).longValue()))
                .collect(Collectors.toList());
        return new PageImpl<>(tourInstanceResponse, pageable, tourInstancesPage.getTotalElements());
    }

    @Override
    public TourInstanceDto findTourInstanceById(Long tourInstanceId) {
        TourInstance tourInstance = tourInstanceRepository.findById(tourInstanceId).orElseThrow(
                () -> new CustomException(ErrorCode.TOUR_INSTANCE_NOT_FOUND,"Can't found tour instance",HttpStatus.NOT_FOUND)
        );
        return tourInstanceMapper.toDto(tourInstance);
    }


    private void setTourGuideIfPresent(TourInstance tourInstance, String tourGuideId) {
        if (tourGuideId == null) return;

        User tourGuide = findTourGuideById(tourGuideId);
        tourInstance.setTourGuide(tourGuide);
    }

    private void setTourPackage(TourInstance tourInstance, String tourPackageId) {
        if (!tourPackageRepository.existsById(tourPackageId)) {
            throw new CustomException(ErrorCode.TOUR_PACKAGE_NOT_FOUND,
                    "Can't found tour package", HttpStatus.NOT_FOUND);
        }
        TourPackage tourPackage = new TourPackage();
        tourPackage.setId(tourPackageId);
        tourInstance.setTourPackage(tourPackage);
    }

    private void updateTourGuide(TourInstance tourInstance, String newTourGuideId) {
        if (Objects.equals(tourInstance.getTourGuide() != null ? tourInstance.getTourGuide().getId() : null, newTourGuideId)) {
            return;  // No change in tour guide
        }
        User newTourGuide = (newTourGuideId != null) ? findTourGuideById(newTourGuideId) : null;
        tourInstance.setTourGuide(newTourGuide);
    }

    private User findTourGuideById(String tourGuideId) {
        User user = userRepository.findById(tourGuideId)
                .orElseThrow(() -> new CustomException(ErrorCode.TOUR_GUIDE_NOT_FOUND,
                        "Can't found tour guide", HttpStatus.NOT_FOUND));
        if (!user.getRole().equals(Role.TOUR_GUIDE)) {
            throw new CustomException(ErrorCode.MUST_HAVE_TOUR_GUIDE_ROLE,
                    "Must have role tour guide", HttpStatus.BAD_REQUEST);
        }
        return user;
    }

    private void updateTourPackage(TourInstance tourInstance, String newTourPackageId) {
        if (tourInstance.getTourPackage().getId().equals(newTourPackageId)) {
            return;  // No change in tour package
        }
        TourPackage tourPackage = tourPackageRepository.findById(newTourPackageId)
                .orElseThrow(() -> new CustomException(ErrorCode.TOUR_PACKAGE_NOT_FOUND,
                        "Can't found tour package", HttpStatus.NOT_FOUND));
        tourInstance.setTourPackage(tourPackage);
    }

    @Override
    @Transactional
    public ConfirmTripCompleteResponse confirmTourInstanceComplete(Long tourInstanceId){
        TourInstance tourInstance = tourInstanceRepository.findById(tourInstanceId).orElseThrow(
                ()-> new CustomException(ErrorCode.TOUR_INSTANCE_NOT_FOUND,"tour instance not found",HttpStatus.BAD_REQUEST)
        );
        if (tourInstance.getIsComplete()){
            throw new CustomException(ErrorCode.TOUR_INSTANCE_ALREADY_COMPLETED,"This trip is already completed",HttpStatus.BAD_REQUEST);
        }
        if (tourInstance.getIsComplete()){
            throw new CustomException(ErrorCode.TOUR_INSTANCE_ALREADY_COMPLETED,"This trip is already completed",HttpStatus.BAD_REQUEST);
        }

        List<Tuple> unreturnedItemsList = assignmentItemRepository.findAllByTourInstance(tourInstanceId);

        List<AssignmentItemNoReturned> listAssignmentItemNoReturned =  unreturnedItemsList.stream().map(tuple -> AssignmentItemNoReturned.builder()
                .responsiblePerson(tuple.get("responsible_person", String.class))
                .item(tuple.get("item_name", String.class))
                .quantity(tuple.get("quantity", Byte.class).intValue())
                .build()
        ).collect(Collectors.toList());

        if(!listAssignmentItemNoReturned.isEmpty()){
            return new ConfirmTripCompleteResponse<>().setCode(400).setData(listAssignmentItemNoReturned);
        }

        tourInstance.setIsComplete(true);
        tourInstanceRepository.save(tourInstance);
        return new ConfirmTripCompleteResponse<String>().setCode(200).setData("ok");
    }

    @Override
    public String getIdByRider(String rider) {
        return tourInstanceRepository.getTourInstanceIdByRider(rider);
    }
}

