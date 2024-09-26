package com.looptracker.looptracker.controller;

import com.looptracker.looptracker.dto.request.TourPackageRequest;
import com.looptracker.looptracker.repository.ITourPackageRepository;
import com.looptracker.looptracker.service.TourPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tour-package")
public class TourPackageController {
    @Autowired
    private TourPackageService tourPackageService;

    @Autowired
    private ITourPackageRepository tourPackageRepository;

    @PostMapping("create")
    ResponseEntity<?> create(@RequestBody TourPackageRequest tourPackageRequest) {
        tourPackageService.createTourPackage(tourPackageRequest);
        return ResponseEntity.ok("Successfully created tour package");
    }

    @GetMapping("get-all")
    ResponseEntity<?> getAll(Pageable pageable){
        return ResponseEntity.ok(tourPackageRepository.findAll( pageable));
    }
}
