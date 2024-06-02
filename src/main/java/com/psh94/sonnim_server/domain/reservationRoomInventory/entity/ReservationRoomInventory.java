package com.psh94.sonnim_server.domain.reservationRoomInventory.entity;

import com.psh94.sonnim_server.common.utils.BaseTimeEntity;
import com.psh94.sonnim_server.domain.reservation.entity.Reservation;
import com.psh94.sonnim_server.domain.roomInventory.entity.RoomInventory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "reservation_room_inventories")
public class ReservationRoomInventory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_room_inventory_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_inventory_id")
    private RoomInventory roomInventory;

    @Builder
    public ReservationRoomInventory(Reservation reservation, RoomInventory roomInventory) {
        this.reservation = reservation;
        this.roomInventory = roomInventory;
    }
}