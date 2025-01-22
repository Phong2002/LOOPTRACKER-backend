package com.looptracker.looptracker.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.looptracker.looptracker.dto.RiderInforDto;
import com.looptracker.looptracker.dto.UserDto;
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
public class CreateUserFormRequest {
    private UserDto user;
    private RiderInforDto riderInfor;
}
