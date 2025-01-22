package com.looptracker.looptracker.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.looptracker.looptracker.entity.enums.DriverStatus;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RiderInforDto {
    private Integer id;
    private String licenseNumber;
    private String citizenIdNumber;
    private DriverStatus riderStatus;
    private String address;
    private String cccdFront;
    private String cccdBack;
    private String gplxFront;
    private String gplxBack;
}
