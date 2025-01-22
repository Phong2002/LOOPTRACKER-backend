package com.looptracker.looptracker.controller;

import com.looptracker.looptracker.dto.ItemDto;
import com.looptracker.looptracker.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {
    @Autowired
    private IItemService itemService;

    @GetMapping("get-all")
    public ResponseEntity<?> getAll(Pageable pageable) {
        return ResponseEntity.ok(itemService.findAllItems(pageable));
    }

    @PostMapping(value = "create")
    public ResponseEntity<?> createItem(@RequestPart String name,
                                        @RequestPart(required = false) MultipartFile image,Integer quantity
    ) throws Exception {
        System.out.println(name);
        itemService.createItem(name,image,quantity);
        return ResponseEntity.ok("Item created");
    }

    @PutMapping("update")
    public ResponseEntity<?> updateItem(@RequestBody ItemDto itemDto) {
        itemService.updateItem(itemDto);
        return ResponseEntity.ok("Item updated");
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteItem(@RequestParam String id) {
        itemService.deleteItem(id);
        return ResponseEntity.ok("Item deleted");
    }
}
