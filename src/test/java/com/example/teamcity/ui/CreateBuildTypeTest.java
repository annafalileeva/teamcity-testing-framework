package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.ui.pages.ProjectPage;
import com.example.teamcity.ui.pages.admin.buildType.CreateBuildTypePage;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.BUILD_TYPES;
import static com.example.teamcity.api.enums.Endpoint.PROJECTS;
import static com.example.teamcity.api.requests.helpers.RequestHelper.getBuildTypesCount;
import static java.util.concurrent.TimeUnit.SECONDS;

@Test(groups = {"Regression"})
public class CreateBuildTypeTest extends BaseUITest {
    private static final String REPO_URL = "https://github.com/annafalileeva/js-examples";

    @Test(description = "User should be able to create build type from a repository URL", groups = {"Positive", "CRUD"})
    public void userCreatesBuildTypeFromRepositoryURLTest() {
        superUserCheckRequests.getRequest(PROJECTS).create(testData.getProject());

        loginAs(testData.getUser());
        CreateBuildTypePage
                .open(testData.getProject().getId())
                .createForm(REPO_URL)
                .setupBuildType(testData.getBuildType().getName());

        var createdBuildType = superUserCheckRequests.<BuildType>getRequest(BUILD_TYPES).read(
                "name:" + testData.getBuildType().getName(), 10, SECONDS, 1, SECONDS);
        softy.assertNotNull(createdBuildType);

        var foundBuildType = ProjectPage
                .open(testData.getProject().getId())
                .getBuildTypes()
                .stream().anyMatch(buildType -> buildType.getName().text().equals(testData.getBuildType().getName()));
        softy.assertTrue(foundBuildType);
    }

    @Test(description = "User should be able to create build type manually", groups = {"Positive", "CRUD"})
    public void userCreatesBuildTypeManuallyTest() {
        superUserCheckRequests.getRequest(PROJECTS).create(testData.getProject());

        loginAs(testData.getUser());
        CreateBuildTypePage
                .open(testData.getProject().getId())
                .createFormManually(testData.getBuildType());

        var createdBuildType = superUserCheckRequests.<BuildType>getRequest(BUILD_TYPES).read(
                "name:" + testData.getBuildType().getName(), 10, SECONDS, 1, SECONDS);
        softy.assertNotNull(createdBuildType);

        var foundBuildType = ProjectPage
                .open(testData.getProject().getId())
                .getBuildTypes()
                .stream().anyMatch(buildType -> buildType.getName().text().equals(testData.getBuildType().getName()));
        softy.assertTrue(foundBuildType);
    }

    @Test(description = "User should not be able to create build type without a repository URL", groups = {"Negative", "CRUD"})
    public void userCreatesBuildTypeWithoutRepositoryURLTest() {
        superUserCheckRequests.getRequest(PROJECTS).create(testData.getProject());
        superUserCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());

        var buildTypesCountBefore = getBuildTypesCount(testData.getProject().getId());

        loginAs(testData.getUser());
        CreateBuildTypePage
                .open(testData.getProject().getId())
                .createEmptyForm()
                .urlError.shouldHave(Condition.exactText("URL must not be empty"));

        var buildTypesCountAfter = getBuildTypesCount(testData.getProject().getId());
        softy.assertEquals(buildTypesCountBefore, buildTypesCountAfter);
    }

    @Test(description = "User should not be able to create build type without name and id", groups = {"Negative", "CRUD"})
    public void userCreatesBuildTypeWithoutNameAndIdTest() {
        superUserCheckRequests.getRequest(PROJECTS).create(testData.getProject());
        superUserCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());

        var buildTypesCountBefore = getBuildTypesCount(testData.getProject().getId());

        loginAs(testData.getUser());
        CreateBuildTypePage
                .open(testData.getProject().getId())
                .createEmptyFormManually()
                .checkFieldsError("Name must not be empty", "The ID field must not be empty.");

        var buildTypesCountAfter = getBuildTypesCount(testData.getProject().getId());
        softy.assertEquals(buildTypesCountBefore, buildTypesCountAfter);
    }
}
