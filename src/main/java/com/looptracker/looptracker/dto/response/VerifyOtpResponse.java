package com.looptracker.looptracker.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class VerifyOtpResponse {
    private int code;
    private String message;
    private boolean success;
}
