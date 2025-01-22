package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.ItemMovementDto;
import com.looptracker.looptracker.dto.request.ItemMovementRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IItemMovementService {
    void addItem(ItemMovementRequest itemMovementRequest);
    void removeItem(ItemMovementRequest itemMovementRequest);
    Page<ItemMovementDto> getItemMovements(Pageable pageable);
    Page<ItemMovementDto> getItemsMovementByItemId(String itemId, Pageable pageable);
}
