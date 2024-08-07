package com.psh94.sonnim_server.domain.roomInventory.controller;

import com.psh94.sonnim_server.domain.roomInventory.dto.RoomInventoryEnrollRequest;
import com.psh94.sonnim_server.domain.roomInventory.service.RoomInventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roomInventory")
@Slf4j
public class RoomInventoryController {

    private final RoomInventoryService roomInventoryService;

    @PostMapping
    public ResponseEntity<?> createRoomInventory(@Valid @RequestBody RoomInventoryEnrollRequest roomInventoryEnrollRequest) {
        roomInventoryService.saveInventory(roomInventoryEnrollRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomInventory(@PathVariable Long id) {
        roomInventoryService.getInventoryById(id);
        return ResponseEntity.ok(roomInventoryService.getInventoryById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoomInventory(@PathVariable Long id) {
        roomInventoryService.deleteInventory(id);
        return ResponseEntity.ok().build();
    }
}
