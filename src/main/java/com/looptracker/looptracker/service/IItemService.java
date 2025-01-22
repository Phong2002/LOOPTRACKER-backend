package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.ItemDto;
import com.looptracker.looptracker.dto.response.ItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IItemService {
    void createItem(String name, MultipartFile image,Integer quantity) throws Exception;
    void updateItem(ItemDto itemDto);
    void deleteItem(String itemId);
    Page<ItemResponse> findAllItems(Pageable pageable);
}
