package com.example.teamcity.ui.pages.project;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.ProjectElement;

import java.util.List;

import com.codeborne.selenide.Condition;
import com.example.teamcity.ui.elements.ProjectTreeElement;
import com.example.teamcity.ui.pages.BasePage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProjectsPage extends BasePage {
    private static final String PROJECTS_URL = "/favorite/projects";

    public ElementsCollection projectElements = $$("div[class*='Subproject__container']");
    private SelenideElement header = $(".MainPanel__router--gF > div");
    private SelenideElement searchInput = $("#search-projects");
    public ElementsCollection foundedProjectElements = $$("[data-test-itemtype='project']");

    public ProjectsPage() {
        header.shouldBe(Condition.visible, BASE_WAITING);
    }

    public static ProjectsPage open() {
        return Selenide.open(PROJECTS_URL, ProjectsPage.class);
    }

    public List<ProjectElement> getProjects() {
        return generatePageElements(projectElements, ProjectElement::new);
    }

    public ProjectsPage searchProject(String projectName) {
        searchInput.val(projectName);
        return this;
    }

    public List<ProjectTreeElement> getFoundedProjects() {
        return generatePageElements(foundedProjectElements, ProjectTreeElement::new);
    }
}