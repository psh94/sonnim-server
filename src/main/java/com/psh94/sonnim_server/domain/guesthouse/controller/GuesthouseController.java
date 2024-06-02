package com.psh94.sonnim_server.domain.guesthouse.controller;

import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseDTO;
import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseEnrollRequest;
import com.psh94.sonnim_server.domain.guesthouse.service.GuesthouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGuesthouse(@PathVariable Long id) {
        guesthouseService.deleteGuesthouse(id);
        return ResponseEntity.ok().build();
    }

}
