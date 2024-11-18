package com.example.teamcity.ui.pages.admin.buildType;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.ui.pages.admin.project.CreateBasePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class CreateBuildTypePage extends CreateBasePage {
    private static final String CREATE_BUILD_TYPE_MODE = "createBuildTypeMenu";

    private SelenideElement buildTypeId = $("#buildTypeExternalId");
    public SelenideElement buildTypeIdError = $("#error_buildTypeExternalId");

    @Step("Open Create build type page")
    public static CreateBuildTypePage open(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, CREATE_BUILD_TYPE_MODE), CreateBuildTypePage.class);
    }

    @Step("Fill URL for project")
    public CreateBuildTypePage createForm(String url) {
        baseCreateForm(url);
        return this;
    }

    @Step("Fill build type form")
    public void setupBuildType(String buildTypeName) {
        buildTypeNameInput.val(buildTypeName);
        submitButton.click();
    }

    @Step("Create build type manually")
    public void createFormManually(BuildType buildType) {
        createManuallyButton.click();
        buildTypeNameInput.val(buildType.getName());
        buildTypeId.val(buildType.getId());
        createButton.click();
    }

    @Step("Submit empty build type form")
    public CreateBuildTypePage createEmptyForm() {
        submitButton.click();
        return this;
    }

    @Step("Create empty build type manually")
    public CreateBuildTypePage createEmptyFormManually() {
        createManuallyButton.click();
        createButton.click();
        return this;
    }

    @Step("Check errors")
    public CreateBuildTypePage checkFieldsError(String nameErrorText, String idErrorText) {
        buildTypeNameError.shouldHave(Condition.exactText(nameErrorText));
        buildTypeIdError.shouldHave(Condition.exactText(idErrorText));
        return this;
    }
}
