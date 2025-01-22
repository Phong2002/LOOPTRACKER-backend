package com.looptracker.looptracker.service;
import com.looptracker.looptracker.dto.TourInstanceDto;
import com.looptracker.looptracker.dto.request.ListIdInTourPackage;
import com.looptracker.looptracker.dto.request.TourInstanceRequest;
import com.looptracker.looptracker.dto.response.ConfirmTripCompleteResponse;
import com.looptracker.looptracker.dto.response.TourInstanceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITourInstanceService {
    void createTourInstance(TourInstanceRequest tourInstanceRequest);
    void updateTourInstance(TourInstanceRequest tourInstanceRequest, ListIdInTourPackage listIdInTourPackage);
    void deleteTourInstance(Long tourInstanceId);
    Page<TourInstanceResponse> getAllTourInstances(String search,String status,String tourPackage,Pageable pageable);
    TourInstanceDto findTourInstanceById(Long tourInstanceId);
    ConfirmTripCompleteResponse confirmTourInstanceComplete(Long tourInstanceId);
    String getIdByRider(String rider);

}
