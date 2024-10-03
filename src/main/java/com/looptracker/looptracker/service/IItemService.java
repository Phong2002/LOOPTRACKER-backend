package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.ItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IItemService {
    void createItem(ItemDto itemDto);
    void updateItem(ItemDto itemDto);
    void deleteItem(String itemId);
    Page<ItemDto> findAllItems(Pageable pageable);
}
