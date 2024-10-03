package com.looptracker.looptracker.controller;

import com.looptracker.looptracker.dto.request.TourInstanceRequest;
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
    public ResponseEntity<?> getAllTourInstances(Pageable pageable) {

        return ResponseEntity.ok(tourInstanceService.getAllTourInstances(pageable));
    }

    @PostMapping("create")
    public ResponseEntity<?> createTourInstance(@RequestBody TourInstanceRequest tourInstanceRequest) {
        tourInstanceService.createTourInstance(tourInstanceRequest);
        return ResponseEntity.ok("Successfully created tour instance");
    }

    @PutMapping("update")
    public ResponseEntity<?> updateTourInstance(@RequestBody TourInstanceRequest tourInstanceRequest) {
        tourInstanceService.updateTourInstance(tourInstanceRequest);
        return ResponseEntity.ok("Successfully updated tour instance");
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteTourInstance(@RequestParam Long tourInstanceId) {
        tourInstanceService.deleteTourInstance(tourInstanceId);
        return ResponseEntity.ok("Successfully deleted tour instance");
    }
}
