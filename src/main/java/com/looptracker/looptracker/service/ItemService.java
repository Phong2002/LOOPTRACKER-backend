package com.looptracker.looptracker.service;

import com.looptracker.looptracker.dto.ItemDto;
import com.looptracker.looptracker.dto.response.ItemResponse;
import com.looptracker.looptracker.dto.response.TourInstanceResponse;
import com.looptracker.looptracker.entity.Item;
import com.looptracker.looptracker.entity.ItemMovement;
import com.looptracker.looptracker.entity.enums.ItemMovementType;
import com.looptracker.looptracker.exception.CustomException;
import com.looptracker.looptracker.exception.ErrorCode;
import com.looptracker.looptracker.mapper.ItemMapper;
import com.looptracker.looptracker.repository.ItemMovementRepository;
import com.looptracker.looptracker.repository.ItemRepository;
import com.looptracker.looptracker.service.storage.IMinioService;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItemService implements IItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemMovementRepository itemMovementRepository;
    @Autowired
    private IMinioService minioService;

    @Override
    @Transactional
    public void createItem(String name, MultipartFile image,Integer quantity) throws Exception {
        Item item = new Item();
        item.setName(name);

        String path = "item";
        String imageName = UUID.randomUUID().toString();
        String pathImage = minioService.uploadFile(path,imageName,image.getInputStream(),image.getContentType());
        item.setImage(pathImage);
        item.setType("ITEM");
        itemRepository.saveAndFlush(item);

        if(quantity!=null && quantity>0) {
            ItemMovement itemMovement = new ItemMovement();
            LocalDateTime now = LocalDateTime.now();
            itemMovement.setTime(now);
            itemMovement.setItem(item);
            itemMovement.setQuantity(quantity);
            itemMovement.setDescription("Nhập kho lần đầu");
            itemMovement.setType(ItemMovementType.ADDED);
            itemMovementRepository.save(itemMovement);
        }
    }

    @Override
    @Transactional
    public void updateItem(ItemDto itemDto) {
        Item item = itemRepository.findById(itemDto.getId()).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND,"Item not found", HttpStatus.BAD_REQUEST)
        );
        item.setType(itemDto.getType());
        item.setName(itemDto.getName());
        itemRepository.save(item);
    }

    @Override
    @Transactional
    public void deleteItem(String itemId) {
        if(itemMovementRepository.existsById(itemId)) {
            throw new CustomException(ErrorCode.CAN_NOT_DELETE,"Can't delete item", HttpStatus.BAD_REQUEST);
        }
        itemRepository.deleteById(itemId);
    }

    @Override
    public Page<ItemResponse> findAllItems(Pageable pageable) {
        Page<Tuple> itemsPage = itemRepository.findAllItems(pageable);

        List<ItemResponse> tourInstanceResponse = itemsPage.stream()
                .map(tuple -> new ItemResponse()
                        .setId(tuple.get("id", String.class))
                        .setName(tuple.get("name",String.class))
                        .setType(tuple.get("type",String.class))
                        .setImage(tuple.get("image",String.class))
                        .setTotalImport(Optional.ofNullable(tuple.get("total_import",BigDecimal.class))
                                .map(BigDecimal::intValue)
                                .orElse(null))
                        .setTotalExport(Optional.ofNullable(tuple.get("total_export",BigDecimal.class))
                                .map(BigDecimal::intValue)
                                .orElse(null))
                        .setTotalBorrowing(Optional.ofNullable(tuple.get("total_borrowing",BigDecimal.class))
                                .map(BigDecimal::intValue)
                                .orElse(null))
                ).collect(Collectors.toList());
        return new PageImpl<>(tourInstanceResponse, pageable, itemsPage.getTotalElements());
    }
}
