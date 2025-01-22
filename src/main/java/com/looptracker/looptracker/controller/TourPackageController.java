package com.looptracker.looptracker.controller;

import com.looptracker.looptracker.dto.request.TourPackageRequest;
import com.looptracker.looptracker.dto.request.TourPackageUpdateRequest;
import com.looptracker.looptracker.dto.request.UserLocation;
import com.looptracker.looptracker.mapper.TourPackageMapper;
import com.looptracker.looptracker.service.ITourPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tour-package")
public class TourPackageController {
    @Autowired
    private ITourPackageService tourPackageService;

    @PostMapping("create")
    ResponseEntity<?> create(@RequestBody TourPackageRequest tourPackageRequest) {
        tourPackageService.createTourPackage(tourPackageRequest);
        return ResponseEntity.ok("Successfully created tour package");
    }

    @GetMapping("get-all")
    ResponseEntity<?> getAll(@RequestParam(required = false) String search,
                             @RequestParam(required = false) Integer day,
                             @RequestParam(required = false) Long minPrice,
                             @RequestParam(required = false) Long maxPrice,
                                                             Pageable pageable){
        return ResponseEntity.ok(tourPackageService.getAllTourPackages(search,day,minPrice,maxPrice,pageable));
    }

    @GetMapping("get/{id}")
    ResponseEntity<?> getAll(@PathVariable String id){
        return ResponseEntity.ok(tourPackageService.getTourPackageById(id));
    }

    @PutMapping("update")
    ResponseEntity<?> update(@RequestBody TourPackageUpdateRequest tourPackageUpdateRequest ) {
        tourPackageService.updateTourPackage(tourPackageUpdateRequest);
        return ResponseEntity.ok("Successfully created tour package");
    }

    @DeleteMapping("delete")
    ResponseEntity<?> delete(@RequestParam String tourPackageId) {
        tourPackageService.deleteTourPackage(tourPackageId);
        return ResponseEntity.ok("Successfully deleted tour package");
    }
}
