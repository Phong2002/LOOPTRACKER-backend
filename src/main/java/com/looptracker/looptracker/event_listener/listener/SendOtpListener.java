package com.looptracker.looptracker.event_listener.listener;

import com.looptracker.looptracker.event_listener.event.SendOtpEvent;
import com.looptracker.looptracker.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class SendOtpListener {
    @Autowired
    private IEmailService emailService;
    @Async
    @EventListener
    public void sendOtpAfterRegistration(SendOtpEvent sendOtpEvent) {
        String email = sendOtpEvent.getEmail();
        Map<String,Object> model = sendOtpEvent.getModel();
        emailService.send(
                email,
                "OTP xác nhận đăng ký trở thành Easy Rider ",
                "otp_template.html",
                model
        );
    }
}
