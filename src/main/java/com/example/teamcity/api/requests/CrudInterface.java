package com.example.teamcity.api.requests;

import com.example.teamcity.api.models.BaseModel;

public interface CrudInterface {
    Object create(BaseModel model);
    Object read(String locator);
    Object update(String locator, BaseModel model);
    Object delete(String locator);
}