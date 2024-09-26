package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.TourInstanceDto;
import com.looptracker.looptracker.dto.request.TourInstanceRequest;
import com.looptracker.looptracker.entity.TourInstance;
import org.springframework.data.domain.Page;

public interface ITourInstanceService {
    void createTourInstance(TourInstanceRequest tourInstanceRequest);
    void updateTourInstance(TourInstanceRequest tourInstanceRequest);
    void deleteTourInstance(String tourInstanceId);
    Page<TourInstanceDto> getAllTourInstances(TourInstanceRequest tourInstanceRequest);

}
