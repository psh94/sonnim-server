package com.psh94.sonnim_server.domain.room.controller;

import com.psh94.sonnim_server.common.auth.CheckRole;
import com.psh94.sonnim_server.domain.room.dto.RoomDTO;
import com.psh94.sonnim_server.domain.room.dto.RoomEnrollRequest;
import com.psh94.sonnim_server.domain.room.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/rooms")
    @CheckRole({"ADMIN","GUESTHOUSE"})
    public ResponseEntity<?> enrollRoom(@Valid @RequestBody RoomEnrollRequest roomEnrollRequest) {
        RoomDTO roomDTO = roomService.enrollRoom(roomEnrollRequest);
        return ResponseEntity.ok(roomDTO);
    }

    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<?> getRoomById(@PathVariable Long roomId) {
        RoomDTO roomDTO = roomService.getRoomById(roomId);
        return ResponseEntity.ok(roomDTO);
    }

    @GetMapping("/guesthouse/{guesthouseId}/rooms")
    public ResponseEntity<?> getRoomsByMemberId(@PathVariable Long guesthouseId) {
        List<RoomDTO> roomsDTO = roomService.getRoomsByGuesthouseId(guesthouseId);
        return ResponseEntity.ok(roomsDTO);
    }

    @DeleteMapping("/rooms/{id}")
    @CheckRole({"ADMIN","GUESTHOUSE"})
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok().build();
    }
}
