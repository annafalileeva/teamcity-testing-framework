package com.example.teamcity.ui.pages.build;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.BasePage;

import static com.codeborne.selenide.Selenide.$;

public class ViewBuildPage extends BasePage {
    private static final String VIEW_BUILD_URL = "/buildConfiguration/%s/%d";

    private SelenideElement title = $("[class*='BuildPageHeader__title']");
    public SelenideElement buildStatus = $("[class*='BuildPageHeader__description']");

    public static ViewBuildPage open(String buildTypeId, Long buildId) {
        return Selenide.open(VIEW_BUILD_URL.formatted(buildTypeId, buildId), ViewBuildPage.class);
    }

    public ViewBuildPage() {
        title.shouldBe(Condition.visible, BASE_WAITING);
    }
}
