package com.psh94.sonnim_server.domain.guesthouse.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GuesthouseDTO {
    private Long id;
    private String guesthouseName;
    private String ownerName;
    private String address;
    private String phone;
    private String description;

    @Builder
    public GuesthouseDTO(Long id, String guesthouseName, String ownerName, String address, String phone, String description) {
        this.id = id;
        this.guesthouseName = guesthouseName;
        this.ownerName = ownerName;
        this.address = address;
        this.phone = phone;
        this.description = description;
    }
}
