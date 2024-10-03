package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.request.ItemMovementRequest;

public interface IItemMovementService {
    void addItem(ItemMovementRequest itemMovementRequest);
    void removeItem(ItemMovementRequest itemMovementRequest);
}
