package com.example.teamcity.ui.pages.admin.buildStep;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class EditCommandLineBuildStepPage extends EditBuildStepBasePage {

    private SelenideElement customScriptTextarea = $(".CodeMirror");

    public static EditCommandLineBuildStepPage open(String buildTypeId) {
        return Selenide.open(EDIT_BUILD_STEP_URL.formatted(EDIT_MODE, buildTypeId, NEW_STEP_MODE), EditCommandLineBuildStepPage.class);
    }

    public static EditCommandLineBuildStepPage open(String buildTypeId, String buildStepId) {
        return Selenide.open(EDIT_BUILD_STEP_URL.formatted(EDIT_MODE, buildTypeId, buildStepId), EditCommandLineBuildStepPage.class);
    }

    public EditCommandLineBuildStepPage setCustomScript(String script) {
        executeJavaScript(
                "arguments[0].CodeMirror.setValue(arguments[1]);",
                customScriptTextarea, script
        );
        return this;
    }

    public void saveChanges() {
        saveButton.click();
        buildStepUpdatesSuccessMessage.shouldBe(Condition.visible, BASE_WAITING);
    }
}
