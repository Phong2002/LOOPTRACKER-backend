package com.looptracker.looptracker.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder
public class NotificationUserDto {
    private Long id;
    private String userId;
    private NotificationDto notification;
    private Boolean isRead;
    private LocalDateTime readAt;
}
