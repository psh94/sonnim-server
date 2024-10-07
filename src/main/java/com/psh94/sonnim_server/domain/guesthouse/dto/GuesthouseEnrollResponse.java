package com.psh94.sonnim_server.domain.guesthouse.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GuesthouseEnrollResponse {

    private Long id;
    private String guesthouseName;
    private String ownerName;

    @Builder
    public GuesthouseEnrollResponse(Long id, String guesthouseName, String ownerName) {
        this.id = id;
        this.guesthouseName = guesthouseName;
        this.ownerName = ownerName;
    }
}

