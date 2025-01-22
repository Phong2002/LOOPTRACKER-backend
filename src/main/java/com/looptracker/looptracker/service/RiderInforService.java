package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.response.RiderInforResponse;
import com.looptracker.looptracker.entity.RiderInfor;
import com.looptracker.looptracker.entity.User;
import com.looptracker.looptracker.entity.enums.DriverStatus;
import com.looptracker.looptracker.exception.CustomException;
import com.looptracker.looptracker.exception.ErrorCode;
import com.looptracker.looptracker.mapper.RiderInforMapper;
import com.looptracker.looptracker.repository.IRiderInforRepository;
import com.looptracker.looptracker.repository.IUserRepository;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class RiderInforService implements IRiderInforService {
    @Autowired
    private IRiderInforRepository riderInforRepository;
    @Autowired
    private RiderInforMapper riderInforMapper;
    @Autowired
    private IUserRepository userRepository;

    public RiderInforResponse getRiderInfor(String riderId) {
        Tuple tuple = riderInforRepository.findRiderInfoByUserId(riderId);
        if (tuple != null) {
            RiderInforResponse response = new RiderInforResponse();

            response.setId(tuple.get(0, Integer.class));
            response.setLicenseNumber(tuple.get(1, String.class));
            response.setCitizenIdNumber(tuple.get(2, String.class));

            String riderStatusStr = tuple.get(3, String.class);
            DriverStatus riderStatus = DriverStatus.valueOf(riderStatusStr);
            response.setRiderStatus(riderStatus);

            response.setAddress(tuple.get(4, String.class));
            response.setCccdFront(tuple.get(5, String.class));
            response.setCccdBack(tuple.get(6, String.class));
            response.setGplxFront(tuple.get(7, String.class));
            response.setGplxBack(tuple.get(8, String.class));
            response.setCreateAt(tuple.get("create_at", Timestamp.class).toLocalDateTime());
            response.setTotalTrips(Math.toIntExact(tuple.get("total_trips", Long.class)));

            return response;
        }
        return null;
    }



    @Override
    @Transactional
    public void updateRiderStatus(DriverStatus driverStatus, String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND,"User not found", HttpStatus.NOT_FOUND)
        );
        RiderInfor riderInfor = user.getRiderInfors();
        if(riderInfor.getRiderStatus().equals(DriverStatus.ON_TRIP)){
            throw new CustomException(ErrorCode.CAN_NOT_UPDATE_DRIVER_STATUS,"Can't udpate rider status",HttpStatus.BAD_REQUEST);
        }else {
            riderInfor.setRiderStatus(driverStatus);
            riderInforRepository.save(riderInfor);
        }
    }
}
