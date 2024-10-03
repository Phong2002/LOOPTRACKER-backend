package com.looptracker.looptracker.controller;

import com.looptracker.looptracker.dto.request.TourAssignmentRequest;
import com.looptracker.looptracker.service.ITourAssignmentService;
import com.looptracker.looptracker.service.TourInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tour-assignment")
public class TourAssignmentController {
    @Autowired
    private ITourAssignmentService tourAssignmentService;
    @Autowired
    private TourInstanceService tourInstanceService;

    @PostMapping("create")
    public ResponseEntity<?> createTourAssignment(@RequestBody TourAssignmentRequest tourAssignmentRequest){
        tourAssignmentService.addTourAssignment(tourAssignmentRequest);
        return ResponseEntity.ok("Create tour assignment successfully");
    }

    @PutMapping("update")
    public ResponseEntity<?> updateTourAssignment(@RequestBody TourAssignmentRequest tourAssignmentRequest){
        tourAssignmentService.updateTourAssignment(tourAssignmentRequest);
        return ResponseEntity.ok("Update tour assignment successfully");
    }

    @GetMapping("get-all")
    public ResponseEntity<?> getAllByTourInstanceId(@RequestParam(name = "tourId")String tourId, Pageable pageable){
        return ResponseEntity.ok(tourAssignmentService.getAllTourAssignments(pageable));
    }
}
