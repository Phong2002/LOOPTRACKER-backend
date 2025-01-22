package com.looptracker.looptracker.dto.request;

import lombok.Data;

@Data
public class UpdateInforRequest {
    private String address;
    private String email;
    private String phoneNumber;
}
