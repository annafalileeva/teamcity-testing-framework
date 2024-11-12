package com.example.teamcity.ui.pages.admin.buildType;

import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.BasePage;

import static com.codeborne.selenide.Selenide.$;

public abstract class EditBuildTypeBasePage extends BasePage {
    protected static final String EDIT_BUILD_TYPE_URL ="/admin/edit%s.html?id=buildType:%s";

    protected SelenideElement buildStepUpdatesSuccessMessage = $("#unprocessed_buildRunnerSettingsUpdated");
    protected SelenideElement saveButton = $("[name='submitButton']");
}
