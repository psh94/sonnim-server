package com.psh94.sonnim_server.domain.guesthouse.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class GuesthouseEnrollRequest {

    @NotBlank
    private String guesthouseName;

    @NotBlank
    private String ownerName;

    @NotBlank
    private String regionCode;

    @NotBlank
    private String detailAddress;

    @NotBlank
    private String phone;

    @NotBlank
    private String description;

    @Builder
    public GuesthouseEnrollRequest(String guesthouseName, String ownerName, String regionCode, String detailAddress, String phone, String description) {
        this.guesthouseName = guesthouseName;
        this.ownerName = ownerName;
        this.regionCode = regionCode;
        this.detailAddress = detailAddress;
        this.phone = phone;
        this.description = description;
    }
}
