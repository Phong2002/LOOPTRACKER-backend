package com.looptracker.looptracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.looptracker.looptracker.dto.IncidentDto;
import com.looptracker.looptracker.dto.request.IncidentRequest;
import com.looptracker.looptracker.entity.Incident;
import com.looptracker.looptracker.service.IIncidenService;
import com.looptracker.looptracker.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/incident")
public class IncidentController {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IncidentService incidentService;

    @PostMapping("create")
    public ResponseEntity<?> createIncident(
            @RequestPart("incident") String incidentJson,
            @RequestPart("images") List<MultipartFile> images) {
        try {
            IncidentRequest incidentRequest = objectMapper.readValue(incidentJson, IncidentRequest.class);
            incidentService.create(incidentRequest,images);
            return ResponseEntity.status(HttpStatus.CREATED).body("Incident created successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid incident data");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("tour/{tourPackageId}")
    public ResponseEntity<List<IncidentDto>> getIncidentsByTourPackageId(
            @PathVariable String tourPackageId) {
        List<IncidentDto> incidents = incidentService.getIncidentsByTourPackageId(tourPackageId);
        return ResponseEntity.ok(incidents);
    }
}
