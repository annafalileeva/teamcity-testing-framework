package com.example.teamcity.ui.pages.admin.buildStep;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class EditCommandLineBuildStepPage extends EditBuildStepBasePage {

    private SelenideElement customScriptTextarea = $(".CodeMirror");

    @Step("Open Create step page for CommandLine step")
    public static EditCommandLineBuildStepPage open(String buildTypeId) {
        return Selenide.open(EDIT_BUILD_STEP_URL.formatted(EDIT_MODE, buildTypeId, NEW_STEP_MODE), EditCommandLineBuildStepPage.class);
    }

    @Step("Open Edit step page for CommandLine step")
    public static EditCommandLineBuildStepPage open(String buildTypeId, String buildStepId) {
        return Selenide.open(EDIT_BUILD_STEP_URL.formatted(EDIT_MODE, buildTypeId, buildStepId), EditCommandLineBuildStepPage.class);
    }

    @Step("Set custom script")
    public EditCommandLineBuildStepPage setCustomScript(String script) {
        executeJavaScript(
                "arguments[0].CodeMirror.setValue(arguments[1]);",
                customScriptTextarea, script
        );
        return this;
    }

    @Step("Save changes")
    public void saveChanges() {
        saveButton.click();
        buildStepUpdatesSuccessMessage.shouldBe(Condition.visible, BASE_WAITING);
    }
}
