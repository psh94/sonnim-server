package com.psh94.sonnim_server.domain.room.entity;

import com.psh94.sonnim_server.common.utils.BaseTimeEntity;
import com.psh94.sonnim_server.domain.guesthouse.entity.Guesthouse;
import com.psh94.sonnim_server.domain.roomInventory.entity.RoomInventory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "rooms")
public class Room extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    private String roomName;
    private int maxCapacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guesthouse_id")
    private Guesthouse guesthouse;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomInventory> roomInventories = new ArrayList<>();

    @Builder
    public Room(String roomName, int maxCapacity, Guesthouse guesthouse) {
        this.roomName = roomName;
        this.maxCapacity = maxCapacity;
        this.guesthouse = guesthouse;
    }

    public void addRoomInventory(RoomInventory roomInventory) {
        roomInventories.add(roomInventory);
        roomInventory.setRoom(this);
    }

    public void removeRoomInventory(RoomInventory roomInventory) {
        roomInventories.remove(roomInventory);
        roomInventory.setRoom(null);
    }
}
