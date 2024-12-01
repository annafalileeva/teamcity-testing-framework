package com.example.teamcity.api.enums;

import com.example.teamcity.api.models.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Endpoint {
    BUILD_TYPES("/app/rest/buildTypes", BuildType.class, BuildTypeList.class),
    PROJECTS("/app/rest/projects", Project.class, ProjectList.class),
    USERS("/app/rest/users", User.class, UserList.class),
    BUILD_QUEUES("/app/rest/buildQueue", Build.class, BuildList.class),
    BUILDS("/app/rest/builds", Build.class, BuildList.class),
    AUTH_SETTINGS("/app/rest/server/authSettings", AuthSettings.class, AuthSettings.class),
    AGENTS("/app/rest/agents", Agent.class, AgentList.class);

    private final String url;
    private final Class<? extends BaseModel> modelClass;
    private final Class<? extends BaseModel> searchResultsModelClass;
}
