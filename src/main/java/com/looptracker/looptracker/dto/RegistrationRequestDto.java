package com.looptracker.looptracker.dto;

import com.looptracker.looptracker.entity.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class RegistrationRequestDto {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String email;
    private String licenseNumber;
    private String citizenIdNumber;

}