package com.looptracker.looptracker.dto.request;

import lombok.Data;

@Data
public class ValidateRegistrationRequestForm {
    String email;
    String phoneNumber;
    String cccd;
    String gplx;
}
