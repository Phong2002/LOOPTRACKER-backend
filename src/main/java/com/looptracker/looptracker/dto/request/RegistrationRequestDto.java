package com.looptracker.looptracker.dto.request;

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
    private String gender;
    private String email;
    private String licenseNumber;
    private String citizenIdNumber;

}