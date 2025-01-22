package com.looptracker.looptracker.controller;

import com.looptracker.looptracker.dto.request.ListIdInTourPackage;
import com.looptracker.looptracker.dto.request.TourInstanceRequest;
import com.looptracker.looptracker.dto.request.TourInstanceUpdateRequest;
import com.looptracker.looptracker.service.ITourInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tour-instance")
public class TourInstanceController {
    @Autowired
    private ITourInstanceService tourInstanceService;

    @GetMapping("get-all")
    public ResponseEntity<?> getAllTourInstances(@RequestParam(required = false) String search,
                                                 @RequestParam(required = false) String status,
                                                 @RequestParam(required = false) String tourPackage,
                                                 Pageable pageable) {

        return ResponseEntity.ok(tourInstanceService.getAllTourInstances(search,status,tourPackage,pageable));
    }

    @GetMapping("details")
    public ResponseEntity<?> getDetailsTourInstances(@RequestParam(required = false) Long tourInstanceId){

        return ResponseEntity.ok(tourInstanceService.findTourInstanceById(tourInstanceId));

    }

    @PutMapping("confirm-complete")
    public ResponseEntity<?> tourInstanceComplete(@RequestParam Long tourInstanceId){

        return ResponseEntity.ok(tourInstanceService.confirmTourInstanceComplete(tourInstanceId));

    }

    @PostMapping("create")
    public ResponseEntity<?> createTourInstance(@RequestBody TourInstanceRequest tourInstanceRequest) {
        tourInstanceService.createTourInstance(tourInstanceRequest);
        return ResponseEntity.ok("Successfully created tour instance");
    }

    @PutMapping("update")
    public ResponseEntity<?> updateTourInstance(@RequestBody TourInstanceUpdateRequest tourInstanceUpdateRequest) {
        tourInstanceService.updateTourInstance(tourInstanceUpdateRequest.getTourInstanceRequest(),tourInstanceUpdateRequest.getListIdInTourPackage());
        return ResponseEntity.ok("Successfully updated tour instance");
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteTourInstance(@RequestParam Long tourInstanceId) {
        tourInstanceService.deleteTourInstance(tourInstanceId);
        return ResponseEntity.ok("Successfully deleted tour instance");
    }

    @GetMapping("get-by-rider")
    public ResponseEntity<?> getTourInstanceByRider(@RequestParam String rider) {
        return ResponseEntity.ok(tourInstanceService.getIdByRider(rider));
    }
}
