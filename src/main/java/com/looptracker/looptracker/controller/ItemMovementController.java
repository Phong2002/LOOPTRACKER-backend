package com.looptracker.looptracker.controller;

import com.looptracker.looptracker.dto.request.ItemMovementRequest;
import com.looptracker.looptracker.service.ItemMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/item-movement")
public class ItemMovementController {
    @Autowired
    private ItemMovementService itemMovementService;

    @GetMapping("get-all")
    public ResponseEntity<?> getAllItemMovements(Pageable pageable) {
        return ResponseEntity.ok(itemMovementService.getItemMovements(pageable));
    }

    @GetMapping("get-by-id/{itemId}")
    public ResponseEntity<?> getAllByItemId(@PathVariable(name = "itemId") String itemId,Pageable pageable){
        return ResponseEntity.ok(itemMovementService.getItemsMovementByItemId(itemId,pageable));
    }

    @PostMapping("add")
    public ResponseEntity<?> addItemMovement(@RequestBody ItemMovementRequest itemMovementRequest){
        itemMovementService.addItem(itemMovementRequest);
        return ResponseEntity.ok("add item success");
    }

    @PostMapping("remove")
    public ResponseEntity<?> removeItemMovement(@RequestBody ItemMovementRequest itemMovementRequest){
        itemMovementService.removeItem(itemMovementRequest);
        return ResponseEntity.ok("remove item success");
    }
}
