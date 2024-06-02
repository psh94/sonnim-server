package com.psh94.sonnim_server.common.converter;

import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseDTO;
import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseEnrollRequest;
import com.psh94.sonnim_server.domain.guesthouse.entity.Guesthouse;

public class GuesthouseConverter {

    public static Guesthouse toEntity(GuesthouseEnrollRequest guesthouseEnrollRequest){
        return Guesthouse.builder()
                .guesthouseName(guesthouseEnrollRequest.getGuesthouseName())
                .ownerName(guesthouseEnrollRequest.getOwnerName())
                .phone(guesthouseEnrollRequest.getPhone())
                .address(guesthouseEnrollRequest.getAddress())
                .description(guesthouseEnrollRequest.getDescription())
                .build();
    }

    public static GuesthouseDTO toDTO(Guesthouse guesthouse){
        return GuesthouseDTO.builder()
                .id(guesthouse.getId())
                .guesthouseName(guesthouse.getGuesthouseName())
                .ownerName(guesthouse.getOwnerName())
                .phone(guesthouse.getPhone())
                .description(guesthouse.getDescription())
                .build();
    }
}
