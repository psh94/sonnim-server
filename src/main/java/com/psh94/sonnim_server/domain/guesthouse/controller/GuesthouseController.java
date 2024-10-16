package com.psh94.sonnim_server.domain.guesthouse.controller;

import com.psh94.sonnim_server.common.auth.CheckRole;
import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseDTO;
import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseEnrollRequest;
import com.psh94.sonnim_server.domain.guesthouse.service.GuesthouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guesthouses")
public class GuesthouseController {

    private final GuesthouseService guesthouseService;

    @CheckRole({"ADMIN"})  // ADMIN 권한을 가진 사용자만 접근 가능
    @PostMapping
    public ResponseEntity<GuesthouseDTO> enrollGuesthouse(@Valid @RequestBody GuesthouseEnrollRequest guesthouseEnrollRequest) {
        GuesthouseDTO savedGuesthouse = guesthouseService.enrollGuesthouse(guesthouseEnrollRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGuesthouse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuesthouseDTO> getGuesthouse(@PathVariable Long id) {
        GuesthouseDTO guesthouse = guesthouseService.getGuesthouse(id);
        return ResponseEntity.ok(guesthouse);
    }

    @GetMapping("/region/{regionCode}")
    public ResponseEntity<List<GuesthouseDTO>> getGuesthousesByRegionCode(@PathVariable String regionCode) {
        List<GuesthouseDTO> guesthouses = guesthouseService.getGuesthousesByRegionCode(regionCode);
        return ResponseEntity.ok(guesthouses);
    }

    @GetMapping("/search")
    public ResponseEntity<List<GuesthouseDTO>> searchGuesthouses(@RequestParam String searchWord) {
        List<GuesthouseDTO> guesthouses = guesthouseService.getGuesthousesByWord(searchWord);
        return ResponseEntity.ok(guesthouses);
    }

    @CheckRole({"ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuesthouse(@PathVariable Long id) {
        guesthouseService.deleteGuesthouse(id);
        return ResponseEntity.noContent().build();
    }
}
