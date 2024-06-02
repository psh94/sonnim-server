package com.psh94.sonnim_server.domain.member.constant;

public enum Role {
    ADMIN("ROLE_ADNIM"),
    USER("ROLE_USER"),
    GUESTHOUSE("ROLE_GUESTHOUSE");

    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
