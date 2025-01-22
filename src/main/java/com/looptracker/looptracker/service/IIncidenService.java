package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.IncidentDto;
import com.looptracker.looptracker.dto.request.IncidentRequest;
import com.looptracker.looptracker.entity.Incident;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IIncidenService {
    void create(IncidentRequest incidentRequest, List<MultipartFile> images) throws Exception;
    List<IncidentDto> getIncidentsByTourPackageId(String tourPackageId);

}
