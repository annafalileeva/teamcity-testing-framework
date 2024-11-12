package com.example.teamcity.ui.pages.admin.buildStep;

import com.example.teamcity.ui.pages.admin.buildType.EditBuildTypeBasePage;

public abstract class EditBuildStepBasePage extends EditBuildTypeBasePage {
    protected static final String EDIT_BUILD_STEP_URL = EDIT_BUILD_TYPE_URL + "&runnerId=%s";
    protected static final String EDIT_MODE = "editRunType";
    protected static final String NEW_STEP_MODE = "__NEW_RUNNER__";
}
