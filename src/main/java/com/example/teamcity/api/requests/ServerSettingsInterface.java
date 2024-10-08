package com.example.teamcity.api.requests;

import com.example.teamcity.api.models.BaseModel;

public interface ServerSettingsInterface {
    Object readSettings();
    Object updateSettings(BaseModel model);
}
