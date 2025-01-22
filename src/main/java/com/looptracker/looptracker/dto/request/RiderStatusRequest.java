package com.looptracker.looptracker.dto.request;

import com.looptracker.looptracker.entity.enums.DriverStatus;
import lombok.Data;

@Data
public class RiderStatusRequest {
    private DriverStatus driverStatus;
}
