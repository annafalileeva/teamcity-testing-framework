package com.example.teamcity.ui;

import com.example.teamcity.api.models.Project;
import com.example.teamcity.ui.pages.project.ProjectsPage;
import org.testng.annotations.Test;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.example.teamcity.api.enums.Endpoint.PROJECTS;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;

@Test(groups = {"Regression"})
public class SearchTest extends BaseUITest {
    @Test(description = "User should be able to search project by name", groups = {"Positive", "Search"})
    public void userSearchProjectByNameTest() {
        superUserCheckRequests.getRequest(PROJECTS).create(testData.getProject());
        superUserCheckRequests.getRequest(PROJECTS).create(generate(Project.class));

        loginAs(testData.getUser());
        var projectsPage = ProjectsPage.open();
        projectsPage
                .searchProject(testData.getProject().getName())
                .foundedProjectElements.shouldHave(size(1));

        var foundedProject = projectsPage.getFoundedProjects().get(0);
        softy.assertTrue(foundedProject.getName().text().equals(testData.getProject().getName()),
                "Wrong founded project name.");
    }
}
