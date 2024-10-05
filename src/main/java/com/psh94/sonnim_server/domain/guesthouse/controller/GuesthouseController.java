package com.psh94.sonnim_server.domain.guesthouse.controller;

import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseDTO;
import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseEnrollRequest;
import com.psh94.sonnim_server.domain.guesthouse.service.GuesthouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guesthouses")
public class GuesthouseController {

    private final GuesthouseService guesthouseService;

    @PostMapping
    public ResponseEntity<?> enrollGuesthouse(@Valid @RequestBody GuesthouseEnrollRequest guesthouseEnrollRequest) {
        guesthouseService.enrollGuesthouse(guesthouseEnrollRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGuesthouse(@PathVariable Long id) {
        GuesthouseDTO guesthouse = guesthouseService.getGuesthouse(id);
        return ResponseEntity.ok(guesthouse);
    }

    @GetMapping("/searchRegion")
    public ResponseEntity<?> getGuesthousesByRegionCode(String regionCode) {
        List<GuesthouseDTO> guesthouse = guesthouseService.getGuesthouseListByRegionCode(regionCode);
        return ResponseEntity.ok(guesthouse);
    }

    @GetMapping("/searchWord")
    public ResponseEntity<?> searchGuesthouses(String searchWord) {
        List<GuesthouseDTO> guesthouses = guesthouseService.getGuesthouseListByWord(searchWord);
        return ResponseEntity.ok(guesthouses);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGuesthouse(@PathVariable Long id) {
        guesthouseService.deleteGuesthouse(id);
        return ResponseEntity.ok().build();
    }

}
