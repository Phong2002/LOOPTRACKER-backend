package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.ItemMovementDto;
import com.looptracker.looptracker.dto.request.ItemMovementRequest;
import com.looptracker.looptracker.entity.Item;
import com.looptracker.looptracker.entity.ItemMovement;
import com.looptracker.looptracker.entity.enums.ItemMovementType;
import com.looptracker.looptracker.mapper.ItemMovementMapper;
import com.looptracker.looptracker.repository.ItemMovementRepository;
import com.looptracker.looptracker.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ItemMovementService implements IItemMovementService {
    @Autowired
    private ItemMovementRepository itemMovementRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemMovementMapper itemMovementMapper;

    @Override
    @Transactional
    public void addItem(ItemMovementRequest itemMovementRequest) {
        itemMovementCommon(itemMovementRequest,ItemMovementType.ADDED);
    }

    @Override
    public void removeItem(ItemMovementRequest itemMovementRequest) {
        itemMovementCommon(itemMovementRequest,ItemMovementType.REMOVED);
    }

    @Override
    public Page<ItemMovementDto> getItemMovements( Pageable pageable) {
        Page<ItemMovement> itemMovements = itemMovementRepository.findAll(pageable);
        return itemMovementMapper.toPageDto(itemMovements);
    }

    @Override
    public Page<ItemMovementDto> getItemsMovementByItemId(String itemId, Pageable pageable) {
        Page<ItemMovement> itemMovements = itemMovementRepository.findByItemId(itemId,pageable);
        return itemMovementMapper.toPageDto(itemMovements);
    }

    private void itemMovementCommon(ItemMovementRequest itemMovementRequest,ItemMovementType itemMovementType) {
        ItemMovement itemMovement = new ItemMovement();
        Item item = itemRepository.findById(itemMovementRequest.getItemId()).orElseThrow(
                () -> new RuntimeException("Item not found")
        );
        itemMovement.setItem(item);
        itemMovement.setDescription(itemMovementRequest.getDescription());
        LocalDateTime now = LocalDateTime.now();
        itemMovement.setTime(now);
        itemMovement.setQuantity(itemMovementRequest.getQuantity());
        itemMovement.setType(itemMovementType);
        itemMovementRepository.save(itemMovement);
    }
}
