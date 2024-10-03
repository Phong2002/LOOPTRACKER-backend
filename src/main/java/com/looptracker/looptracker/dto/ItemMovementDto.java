package com.looptracker.looptracker.dto;

import com.looptracker.looptracker.entity.Item;
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
public class ItemMovementDto {
    private String id;
    private Item item;
    private String type;
    private LocalDateTime time;
    private String description;
    private Integer quantity;
}
