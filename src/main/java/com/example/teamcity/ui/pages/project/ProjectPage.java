package com.example.teamcity.ui.pages.project;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.BuildTypeElement;
import com.example.teamcity.ui.pages.BasePage;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProjectPage extends BasePage {
    private static final String PROJECT_URL = "/project/%s";

    public SelenideElement title = $("span[class*='ProjectPageHeader']");
    private SelenideElement buildTypeList = $("[class*='BuildsByBuildType__container']");
    private ElementsCollection buildTypeElements = $$("div[class*='BuildTypeLine__root']");

    public ProjectPage() {
        title.shouldBe(Condition.visible, BASE_WAITING);
    }

    public static ProjectPage open(String projectId) {
        return Selenide.open(PROJECT_URL.formatted(projectId), ProjectPage.class);
    }

    public List<BuildTypeElement> getBuildTypes() {
        return generatePageElements(buildTypeElements, BuildTypeElement::new);
    }

    public void runBuild(String buildTypeName) {
        buildTypeList.shouldBe(Condition.visible, BASE_WAITING);
        getBuildTypes().stream().filter(buildType -> buildType.getName().text().equals(buildTypeName))
                .findFirst().get().getRunButton().click();
    }
}
