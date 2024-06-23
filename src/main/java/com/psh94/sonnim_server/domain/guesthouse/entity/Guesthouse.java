package com.psh94.sonnim_server.domain.guesthouse.entity;

import com.psh94.sonnim_server.common.utils.BaseTimeEntity;
import com.psh94.sonnim_server.domain.room.entity.Room;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "guesthouses")
public class Guesthouse extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guesthouse_id")
    private Long id;

    @NotBlank
    private String guesthouseName;

    @NotBlank
    private String ownerName;

    @NotBlank
    private String address;

    @NotBlank
    private String phone;

    private String description;

    @OneToMany(mappedBy = "guesthouse", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Room> rooms = new ArrayList<>();

    @Builder
    public Guesthouse(String guesthouseName, String ownerName, String address, String phone, String description) {
        this.guesthouseName = guesthouseName;
        this.ownerName = ownerName;
        this.address = address;
        this.phone = phone;
        this.description = description;
    }
}
