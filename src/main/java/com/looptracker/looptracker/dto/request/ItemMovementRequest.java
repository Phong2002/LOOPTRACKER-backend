package com.looptracker.looptracker.dto.request;
import lombok.Data;

@Data
public class ItemMovementRequest {
    private String itemId;
    private String description;
    private Integer quantity;
}
