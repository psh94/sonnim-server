package com.psh94.sonnim_server.domain.reservation.entity;

import com.psh94.sonnim_server.common.utils.BaseTimeEntity;
import com.psh94.sonnim_server.domain.member.entity.Member;
import com.psh94.sonnim_server.domain.payment.domain.PaymentStatus;
import com.psh94.sonnim_server.domain.reservationRoomInventory.entity.ReservationRoomInventory;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reservations")
public class Reservation extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Column(nullable = false)
    private int headcount;

    @Enumerated(EnumType.STRING)
    @Setter
    @Column(nullable = false)
    private ReservationStatus reservationStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "reservation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReservationRoomInventory> roomInventories = new HashSet<>();

    @Builder
    public Reservation(int headcount, ReservationStatus reservationStatus, Member member, Set<ReservationRoomInventory> roomInventories) {
        this.headcount = headcount;
        this.reservationStatus = reservationStatus;
        this.member = member;
        this.roomInventories = roomInventories;
    }

}
