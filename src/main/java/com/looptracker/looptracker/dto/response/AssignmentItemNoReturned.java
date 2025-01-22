package com.looptracker.looptracker.dto.response;

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
public class AssignmentItemNoReturned {
    private String responsiblePerson;
    private String item;
    private Integer quantity;
}
