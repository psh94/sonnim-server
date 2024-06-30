package com.psh94.sonnim_server.domain.guesthouse.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GuesthouseEnrollRequest {

    @NotBlank
    private String guesthouseName;

    @NotBlank
    private String ownerName;

    @NotBlank
    private String address;

    @NotBlank
    private String phone;

    @NotBlank
    private String description;

    @Builder
    public GuesthouseEnrollRequest(String guesthouseName, String ownerName, String address, String phone, String description) {
        this.guesthouseName = guesthouseName;
        this.ownerName = ownerName;
        this.address = address;
        this.phone = phone;
        this.description = description;
    }
}
