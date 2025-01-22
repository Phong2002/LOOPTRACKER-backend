package com.looptracker.looptracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerResponse {
    private Long id;
    private String passengerFirstName;
    private String passengerLastName;
    private String passengerPhoneNumber;
    private String passengerEmail;
    private String passengerNotes;
    private String passengerGender;
    private String status;
    private String tourPackageName;
    private Integer tourPackageDay;
    private Integer tourPackageNight;
    private String tourPackageDescription;

    private LocalDate startDate;
    private LocalDate endDate;

    private String guideFirstName;
    private String guideLastName;

    private String riderFirstName;
    private String riderLastName;
}
