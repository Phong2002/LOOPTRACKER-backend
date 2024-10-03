package com.looptracker.looptracker.dto;

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
public class ItemDto {
    private String id;
    private String name;
    private String type;
}
