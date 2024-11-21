package com.example.teamcity.ui.pages.admin.project;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class CreateProjectPage extends CreateBasePage {
    private static final String CREATE_PROJECT_MODE="createProjectMenu";

    private SelenideElement projectNameInput = $("#projectName");

    @Step("Open Create project page")
    public static CreateProjectPage open(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, CREATE_PROJECT_MODE), CreateProjectPage.class);
    }

    @Step("Fill URL for project")
    public CreateProjectPage createForm(String url) {
        baseCreateForm(url);
        return this;
    }

    @Step("Fill project form")
    public void setupProject(String projectName, String buildTypeName) {
        projectNameInput.val(projectName);
        buildTypeNameInput.val(buildTypeName);
        submitButton.click();
    }
}
