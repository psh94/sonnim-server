package com.psh94.sonnim_server.domain.guesthouse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(exclude = "id")
@NoArgsConstructor
public class GuesthouseDTO {

    @NotNull
    private Long id;

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
    public GuesthouseDTO(Long id, String guesthouseName, String ownerName, String regionCode, String detailAddress, String phone, String description) {
        this.id = id;
        this.guesthouseName = guesthouseName;
        this.ownerName = ownerName;
        this.regionCode = regionCode;
        this.detailAddress = detailAddress;
        this.phone = phone;
        this.description = description;
    }
}
