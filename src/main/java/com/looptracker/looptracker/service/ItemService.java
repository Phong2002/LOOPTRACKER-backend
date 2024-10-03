package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.ItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ItemService implements IItemService {
    @Override
    public void createItem(ItemDto itemDto) {

    }

    @Override
    public void updateItem(ItemDto itemDto) {

    }

    @Override
    public void deleteItem(String itemId) {

    }

    @Override
    public Page<ItemDto> findAllItems(Pageable pageable) {
        return null;
    }
}
