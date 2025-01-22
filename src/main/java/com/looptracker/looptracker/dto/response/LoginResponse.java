package com.looptracker.looptracker.dto.response;

import com.looptracker.looptracker.entity.enums.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginResponse {
   private String token;
   private String userId;
   private String firstName;
   private String lastName;
   private String email;
   private String phoneNumber;
   private String gender;
   private Role role;
   private String avatarUrl;
   private LocalDateTime createAt;

}
