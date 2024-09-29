package com.psh94.sonnim_server.domain.reservation.controller;

import com.psh94.sonnim_server.domain.reservation.dto.ReservationDTO;
import com.psh94.sonnim_server.domain.reservation.dto.ReservationRequest;
import com.psh94.sonnim_server.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/reservations")
    public ResponseEntity<?> createReservation(@RequestBody ReservationRequest reservationRequest) {
        ReservationDTO reservationDTO = reservationService.createReservation(reservationRequest);
        return ResponseEntity.ok(reservationDTO);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable Long id) {
        ReservationDTO reservationDTO = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservationDTO);
    }

    @GetMapping("/members/{id}/reservations")
    public ResponseEntity<?> getReservationsByMemberId(@PathVariable Long id) {
        List<ReservationDTO> reservations = reservationService.getReservationsByMemberId(id);
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations/{id}")
    public void cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservationById(id);
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }
}
