package com.psh94.sonnim_server.domain.roomInventory.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.psh94.sonnim_server.common.utils.BaseTimeEntity;
import com.psh94.sonnim_server.domain.reservationRoomInventory.entity.ReservationRoomInventory;
import com.psh94.sonnim_server.domain.room.entity.Room;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "roomInventories")
public class RoomInventory extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "roomInventory_id")
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "reservation_date")
    private LocalDate reservationDate;
    private int restCapacity;
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "roomInventory", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReservationRoomInventory> reservations = new HashSet<>();

    @Builder
    public RoomInventory(LocalDate reservationDate, int restCapacity, int price, Room room, Set<ReservationRoomInventory> reservations) {
        this.reservationDate = reservationDate;
        this.restCapacity = restCapacity;
        this.price = price;
        this.room = room;
        this.reservations = reservations;
    }

    public void reduceCapacity(int amount) {
        if (restCapacity < amount) {
            throw new IllegalArgumentException("Insufficient capacity to reduce");
        }
        this.restCapacity -= amount;
    }

    public void addCapacity(int amount) {
        this.restCapacity += amount;
    }
}
