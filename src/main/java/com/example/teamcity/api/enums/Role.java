package com.example.teamcity.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    SYSTEM_ADMIN("SYSTEM_ADMIN", "g"),
    PROJECT_ADMIN("PROJECT_ADMIN", "p:");

    private final String id;
    private final String scope;
}
