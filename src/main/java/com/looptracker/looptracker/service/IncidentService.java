package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.IncidentDto;
import com.looptracker.looptracker.dto.request.IncidentRequest;
import com.looptracker.looptracker.entity.*;
import com.looptracker.looptracker.entity.enums.NotificationAction;
import com.looptracker.looptracker.entity.enums.NotificationType;
import com.looptracker.looptracker.exception.CustomException;
import com.looptracker.looptracker.exception.ErrorCode;
import com.looptracker.looptracker.mapper.IncidentMapper;
import com.looptracker.looptracker.repository.*;
import com.looptracker.looptracker.service.storage.IMinioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class IncidentService implements IIncidenService{
    @Autowired
    private IncidentRepository incidentRepository;
    @Autowired
    private IncidentEvidenceRepository incidentEvidenceRepository;
    @Autowired
    private IncidentMapper incidentMapper;
    @Autowired
    private ITourAssignmentRepository tourAssignmentRepository;
    @Autowired
    private IMinioService minioService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private INotificationUserRepository notificationUserRepository;
    @Autowired
    private INotificationRepository notificationRepository;


    @Override
    @Transactional
    public void create(IncidentRequest incidentRequest, List<MultipartFile> images) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        TourAssignment tourAssignment = tourAssignmentRepository.findById(incidentRequest.getTourAssignments()).orElseThrow(
                ()-> new CustomException(ErrorCode.TOUR_INSTANCE_NOT_FOUND,"Tour instance not found", HttpStatus.NOT_FOUND)
        );
        Incident incident = new Incident();
        incident.setIncidentType(incidentRequest.getIncidentType());
        incident.setDescription(incidentRequest.getDescription());
        incident.setDamageCost(incidentRequest.getDamageCost());
        incident.setLatitude(incidentRequest.getLatitude());
        incident.setLongitude(incidentRequest.getLongitude());
        incident.setTourAssignments(incidentRequest.getTourAssignments());
        incident.setInvolvedRole(incidentRequest.getInvolvedRole());
        incident.setIncidentDate(now);
        incidentRepository.saveAndFlush(incident);

        Notification notification = new Notification();
        notification.setType(NotificationType.TOUR_GUIDE);
        notification.setTitle("Xe của "+getRelatedPerson(tourAssignment)+" đang gặp sự cố");
        notification.setMessage(incident.getDescription());
        notification.setActionKey(NotificationAction.INCIDENT);
        notificationRepository.saveAndFlush(notification);

        NotificationUser notificationUser = new NotificationUser();
        notificationUser.setNotification(notification);
        notificationUser.setUserId(tourAssignment.getTourInstances().getTourGuide().getId());
        notificationUserRepository.saveAndFlush(notificationUser);

        messagingTemplate.convertAndSend("/topic/incident", notification);
        for(MultipartFile image:images){
            String url = processUploadMultipartFile(tourAssignment.getTourInstances().getId().toString(),image);
            IncidentEvidence incidentEvidence = new IncidentEvidence();
            incidentEvidence.setEvidenceUrl(url);
            incidentEvidence.setUploadedAt(now);
            incidentEvidence.setIncident(incident);
            incidentEvidenceRepository.save(incidentEvidence);
        }
    }

    public String getRelatedPerson(TourAssignment tourAssignment){
        if(!Objects.isNull(tourAssignment.getRider())){
            return "Tài xế "+tourAssignment.getRider().getLastName()+" "+tourAssignment.getRider().getFirstName();
        }
        else {
            return "Khách "+tourAssignment.getPassenger().getFirstName()+" "+tourAssignment.getPassenger().getLastName();
        }
    }

    public String processUploadMultipartFile(String tourInstanceId, MultipartFile file) throws Exception {
        String path = "incident/tour-instance-"+tourInstanceId;
        String fileName = UUID.randomUUID().toString();
        String contentType = file.getContentType();
        InputStream fileStream = file.getInputStream();
        return minioService.uploadFile(path,fileName,fileStream,contentType);
    }

    public List<IncidentDto> getIncidentsByTourPackageId(String tourPackageId) {
        // Gọi repository để lấy dữ liệu
        return incidentMapper.toListDto(incidentRepository.findAllByTourPackageId(tourPackageId)) ;
    }

}
