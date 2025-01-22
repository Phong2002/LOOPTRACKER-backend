package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.response.RiderInforResponse;
import com.looptracker.looptracker.entity.enums.DriverStatus;

public interface IRiderInforService {
    RiderInforResponse getRiderInfor(String riderId);
    void updateRiderStatus(DriverStatus driverStatus, String userId);
}
