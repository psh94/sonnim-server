package com.psh94.sonnim_server.domain.reservation.controller;

import com.psh94.sonnim_server.domain.reservation.dto.ReservationDTO;
import com.psh94.sonnim_server.domain.reservation.dto.ReservationRequest;
import com.psh94.sonnim_server.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ReservationDTO createReservation(@RequestBody ReservationRequest reservationRequest) {
        return reservationService.createReservation(reservationRequest);
    }

    @GetMapping("/{id}")
    public ReservationDTO getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }

    @PostMapping("/{id}")
    public void cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservationById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }
}
