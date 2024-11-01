package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.models.Build;
import com.example.teamcity.ui.pages.ProjectPage;
import com.example.teamcity.ui.pages.admin.buildStep.EditCommandLineBuildStepPage;
import com.example.teamcity.ui.pages.admin.buildType.EditBuildStepsPage;
import com.example.teamcity.ui.pages.build.ViewBuildPage;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.*;
import static com.example.teamcity.api.requests.helpers.RequestHelper.waitForBuildStatus;
import static java.util.concurrent.TimeUnit.SECONDS;

@Test(groups = {"Regression"})
public class RunBuildTypeTest extends BaseUITest {
    @Test(description = "User should be able to run build", groups = {"Positive"})
    public void userRunsBuildTypeTest() {
        superUserCheckRequests.getRequest(PROJECTS).create(testData.getProject());
        superUserCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());

        loginAs(testData.getUser());
        EditBuildStepsPage
                .open(testData.getBuildType().getId())
                .addBuildStep()
                .selectStepType("Command Line", EditCommandLineBuildStepPage.class)
                .setCustomScript("echo \"Hello world\"")
                .saveChanges();

        ProjectPage
                .open(testData.getProject().getId())
                .runBuild(testData.getBuildType().getName());

        var createdBuildId = superUserCheckRequests.<Build>getRequest(BUILDS).read(
                "buildType:id:" + testData.getBuildType().getId() + ",number:1",
                10, SECONDS, 1, SECONDS).getId();
        softy.assertNotNull(createdBuildId);
        waitForBuildStatus(createdBuildId, "SUCCESS");

        ViewBuildPage
                .open(testData.getBuildType().getId(), createdBuildId)
                .buildStatus.shouldBe(Condition.exactText("Success"));;
    }
}