package com.looptracker.looptracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.looptracker.looptracker.entity.enums.Gender;
import com.looptracker.looptracker.entity.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String username;
    private Gender gender;
    private String email;
    private Role role;
    private Boolean isAccountNonLocked;
}
