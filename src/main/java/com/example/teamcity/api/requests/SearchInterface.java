package com.example.teamcity.api.requests;

import com.example.teamcity.api.enums.Locator;

public interface SearchInterface {
    Object search(Locator locator, String value);
}
