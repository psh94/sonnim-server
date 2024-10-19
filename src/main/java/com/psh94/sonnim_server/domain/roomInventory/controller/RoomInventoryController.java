package com.psh94.sonnim_server.domain.roomInventory.controller;

import com.psh94.sonnim_server.common.auth.CheckRole;
import com.psh94.sonnim_server.domain.roomInventory.dto.RoomInventoryDTO;
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
    @CheckRole({"ADMIN","GUESTHOUSE"})
    public ResponseEntity<?> createRoomInventory(@Valid @RequestBody RoomInventoryEnrollRequest roomInventoryEnrollRequest) {
        roomInventoryService.saveInventory(roomInventoryEnrollRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomInventory(@PathVariable Long id) {
        RoomInventoryDTO roomInventoryDTO = roomInventoryService.getInventoryById(id);
        return ResponseEntity.ok(roomInventoryDTO);
    }

    @DeleteMapping("/{id}")
    @CheckRole({"ADMIN","GUESTHOUSE"})
    public ResponseEntity<?> deleteRoomInventory(@PathVariable Long id) {
        roomInventoryService.deleteInventory(id);
        return ResponseEntity.ok().build();
    }
}
