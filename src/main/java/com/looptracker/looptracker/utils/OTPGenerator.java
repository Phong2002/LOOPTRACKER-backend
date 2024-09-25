package com.looptracker.looptracker.utils;

import java.util.Random;

public class OTPGenerator {

    public static String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Tạo số ngẫu nhiên từ 100000 đến 999999
        return String.valueOf(otp);
    }
}
