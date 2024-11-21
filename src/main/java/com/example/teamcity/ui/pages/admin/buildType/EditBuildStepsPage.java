package com.example.teamcity.ui.pages.admin.buildType;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.admin.buildStep.SelectBuildStepTypePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class EditBuildStepsPage extends EditBuildTypeBasePage {
    private static final String EDIT_BUILD_STEPS_MODE = "BuildRunners";

    private SelenideElement title = $("#restPageTitle");
    private SelenideElement addBuildStepButton = $(".configurationSection > a.btn");

    @Step("Open Edit steps page")
    public static EditBuildStepsPage open(String buildTypeId) {
        return Selenide.open(EDIT_BUILD_TYPE_URL.formatted(EDIT_BUILD_STEPS_MODE, buildTypeId), EditBuildStepsPage.class);
    }

    public EditBuildStepsPage() {
        title.shouldBe(Condition.visible, BASE_WAITING);
    }

    @Step("Add build step")
    public SelectBuildStepTypePage addBuildStep() {
        addBuildStepButton.click();
        return page(SelectBuildStepTypePage.class);
    }
}
