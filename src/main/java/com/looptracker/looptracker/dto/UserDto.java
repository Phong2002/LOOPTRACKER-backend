package com.looptracker.looptracker.dto;

import com.looptracker.looptracker.entity.enums.Gender;
import com.looptracker.looptracker.entity.enums.Role;
import jakarta.persistence.*;
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
public class UserDto {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Gender gender;
    private String email;
    private Role role;
}
