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
public class ItemResponse{
    private String id;
    private String name;
    private String type;
    private String image;
    private Integer totalImport ;
    private Integer totalExport;
    private Integer totalBorrowing;
}
