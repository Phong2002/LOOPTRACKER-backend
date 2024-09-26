package com.looptracker.looptracker.event_listener.listener;

import com.looptracker.looptracker.event_listener.event.SendAccountInforEvent;
import com.looptracker.looptracker.event_listener.event.SendOtpEvent;
import com.looptracker.looptracker.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class SendAccountInforListener {
    @Autowired
    private IEmailService emailService;
    @Async
    @EventListener
    public void sendOtpAfterRegistration(SendAccountInforEvent sendAccountInforEvent) {
        String email = sendAccountInforEvent.getEmail();
        Map<String,Object> model = sendAccountInforEvent.getModel();
        emailService.send(
                email,
                "Đăng ký trở thành Easy Rider thành công ",
                "registration_success_email.html",
                model
        );
    }
}
