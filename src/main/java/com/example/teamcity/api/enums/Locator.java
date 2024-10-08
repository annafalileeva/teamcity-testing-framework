package com.example.teamcity.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Locator {
    NAME("name"),
    ID("id");

    private final String locator;
}
