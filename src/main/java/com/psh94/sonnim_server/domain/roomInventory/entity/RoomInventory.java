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
@Table(name = "room_inventories")
public class RoomInventory extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_inventory_id")
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate;

    @Column(nullable = false)
    private int restCapacity;

    @Column(nullable = false)
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
        if (amount <= 0) {
            throw new IllegalArgumentException("감소시킬 수량은 양수여야 합니다.");
        }
        if (restCapacity < amount) {
            throw new IllegalArgumentException("남은 용량이 부족하여 예약을 처리할 수 없습니다. 현재 남은 용량: " + restCapacity);
        }
        this.restCapacity -= amount;
    }

    public void addCapacity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("추가할 수량은 양수여야 합니다.");
        }
        this.restCapacity += amount;
    }
}
