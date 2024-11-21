package com.example.teamcity.ui.pages.admin.buildStep;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.StepTypeElement;
import com.example.teamcity.ui.pages.admin.buildType.EditBuildTypeBasePage;
import io.qameta.allure.Step;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class SelectBuildStepTypePage extends EditBuildTypeBasePage {
    protected static final String EDIT_MODE = "editRunType";

    protected SelenideElement stepTypeList = $("table[class*='SelectBuildRunners__list']");
    protected ElementsCollection stepTypeElements = $$("tr[data-test*='runner-item']");

    @Step("Open Select build step type page")
    public static SelectBuildStepTypePage open(String buildTypeId) {
        return Selenide.open(EDIT_BUILD_TYPE_URL.formatted(EDIT_MODE, buildTypeId), SelectBuildStepTypePage.class);
    }

    public SelectBuildStepTypePage() {
        stepTypeList.shouldBe(Condition.visible, BASE_WAITING);
    }

    @Step("Get step types")
    public List<StepTypeElement> getStepTypes() {
        return generatePageElements(stepTypeElements, StepTypeElement::new);
    }

    @Step("Select step type")
    public <T extends EditBuildStepBasePage> T selectStepType(String typeName, Class<T> pageClass) {
        getStepTypes().stream().filter(stepType -> stepType.getName().text().equals(typeName))
                .findFirst().get().getName().click();
        return page(pageClass);
    }
}
