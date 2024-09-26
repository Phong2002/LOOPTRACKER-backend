package com.looptracker.looptracker.service;

import java.util.Map;

public interface IEmailService {
    void send(String to, String subject, String template, Map<String,Object> templateModel);
}
