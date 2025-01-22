package com.looptracker.looptracker.dto;

import com.looptracker.looptracker.entity.Item;
import com.looptracker.looptracker.entity.enums.AssignmentStatus;
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
public class AssignmentItemDto {
    private String id;
    private ItemDto item;
    private Byte quantity;
    private AssignmentStatus status;
}
