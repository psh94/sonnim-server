package com.psh94.sonnim_server.domain.room.controller;

import com.psh94.sonnim_server.domain.room.dto.RoomDTO;
import com.psh94.sonnim_server.domain.room.dto.RoomEnrollRequest;
import com.psh94.sonnim_server.domain.room.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<?> enrollRoom(@Valid @RequestBody RoomEnrollRequest roomEnrollRequest) {
        RoomDTO roomDTO = roomService.enrollRoom(roomEnrollRequest);
        return ResponseEntity.ok(roomDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable Long id) {
        RoomDTO roomDTO = roomService.getRoomById(id);
        return ResponseEntity.ok(roomDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok().build();
    }
}
