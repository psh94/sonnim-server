package com.psh94.sonnim_server.domain.guesthouse.controller;

import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseDTO;
import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseEnrollRequest;
import com.psh94.sonnim_server.domain.guesthouse.service.GuesthouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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


    @GetMapping("/search/region")
    public ResponseEntity<Page<GuesthouseDTO>> getGuesthousesByRegionCode(
            @RequestParam String regionCode,
            @RequestParam(defaultValue = "1") int page,  // 기본 값으로 0페이지를 설정
            @RequestParam(defaultValue = "20") int size  // 기본 크기는 20개로 설정
    ) {
        Page<GuesthouseDTO> guesthouses = guesthouseService.getGuesthousesByRegionCode(regionCode, page, size);
        return ResponseEntity.ok(guesthouses);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<GuesthouseDTO>> getGuesthousesByWord(
            @RequestParam String searchWord,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<GuesthouseDTO> guesthouses = guesthouseService.getGuesthousesByWord(searchWord, page, size);
        return ResponseEntity.ok(guesthouses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGuesthouse(@PathVariable Long id) {
        guesthouseService.deleteGuesthouse(id);
        return ResponseEntity.ok().build();
    }

}
