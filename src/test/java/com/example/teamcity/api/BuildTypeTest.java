package com.example.teamcity.api;

import com.example.teamcity.api.models.*;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.example.teamcity.api.enums.Endpoint.*;
import static com.example.teamcity.api.enums.Role.PROJECT_ADMIN;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;
import static io.qameta.allure.Allure.step;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;


@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseApiTest {
    @Test(description = "User should be able to create build type", groups = {"Positive", "CRUD"})
    public void userCreatesBuildTypeTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());
        var createdBuildType = userCheckRequests.<BuildType>getRequest(BUILD_TYPES).read(testData.getBuildType().getId());

        softy.assertEquals(testData.getBuildType().getName(), createdBuildType.getName(), "Build type name is not correct");
    }

    @Test(description = "User should not be able to create two build types with the same id", groups = {"Negative", "CRUD"})
    public void userCreatesTwoBuildTypesWithTheSameIdTest() {
        var buildTypeWithSameId = generate(Arrays.asList(testData.getProject()), BuildType.class, testData.getBuildType().getId());

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());
        new UncheckedBase(Specifications.authSpec(testData.getUser()), BUILD_TYPES)
                .create(buildTypeWithSameId)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("The build configuration / template ID \"%s\" is already used by another configuration or template".formatted(testData.getBuildType().getId())));
    }

    @Test(description = "Project admin should be able to create build type for their project", groups = {"Positive", "Roles"})
    public void projectAdminCreatesBuildTypeTest() {
        step("Create project");
        superUserCheckRequests.getRequest(PROJECTS).create(testData.getProject());

        step("Create user-admin for project");
        testData.getUser().setRoles(generate(Roles.class, PROJECT_ADMIN.getId(), PROJECT_ADMIN.getScope() + testData.getProject().getId()));
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        step("Create buildType by user-admin for project");
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        var createdBuildTypeId = userCheckRequests.<BuildType>getRequest(BUILD_TYPES).create(testData.getBuildType()).getId();

        step("Read created buildType");
        var createdBuildType = userCheckRequests.<BuildType>getRequest(BUILD_TYPES).read(createdBuildTypeId);

        step("Check buildType was created with correct data");
        softy.assertEquals(testData.getBuildType().getName(), createdBuildType.getName(),
                "Build type name is not correct");
        softy.assertEquals(testData.getProject().getId(), createdBuildType.getProject().getId(),
                "Project for build type is not correct");
    }

    @Test(description = "Project admin should not be able to create build type for not their project", groups = {"Negative", "Roles"})
    public void projectAdminCreatesBuildTypeForAnotherUserProjectTest() {

        step("Create project1");
        superUserCheckRequests.getRequest(PROJECTS).create(testData.getProject());

        step("Create user1-admin for project1");
        testData.getUser().setRoles(generate(Roles.class, PROJECT_ADMIN.getId(), PROJECT_ADMIN.getScope() + testData.getProject().getId()));
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        step("Create project2");
        var project2 = generate(Project.class);
        superUserCheckRequests.getRequest(PROJECTS).create(project2);

        step("Create user2-admin for project2");
        var user2 = generate(User.class);
        user2.setRoles(generate(Roles.class, PROJECT_ADMIN.getId(), PROJECT_ADMIN.getScope() + project2.getId()));
        superUserCheckRequests.getRequest(USERS).create(user2);

        step("Create buildType for project1 by user2 and check it was not created with forbidden code");
        var buildType = generate(Arrays.asList(testData.getProject()), BuildType.class);
        var userUncheckedRequest = new UncheckedRequests(Specifications.authSpec(user2));
        userUncheckedRequest.getRequest(BUILD_TYPES)
                .create(buildType)
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .body(Matchers.containsString(("You do not have enough permissions to edit project with id: %s\n" +
                        "Access denied. Check the user has enough permissions to perform the operation.")
                        .formatted(testData.getProject().getId())));
    }

    @Test(description = "User should be able to run build", groups = {"Positive"})
    public void userRunsBuildTypeTest() {
        step("Create project");
        superUserCheckRequests.getRequest(PROJECTS).create(testData.getProject());

        step("Create user");
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        step("Create buildType with step");
        var buildSteps = generate(Steps.class);
        buildSteps.getStep().get(0).getProperties().setProperty(Arrays.asList(
                generate(Property.class, "script.content", "echo \"Hello world\""),
                generate(Property.class, "teamcity.step.mode", "default"),
                generate(Property.class, "use.custom.script", "true")
        ));
        testData.getBuildType().setSteps(buildSteps);
        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        userCheckedRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());

        step("Run build");
        var buildQueue = generate(Arrays.asList(testData.getBuildType()), BuildQueue.class);
        var createdBuildId = userCheckedRequests.<Build>getRequest(BUILD_QUEUES).create(buildQueue).getId();

        step("Check SUCCESS status for build");
        await()
                .atMost(20, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> {
                    var status = userCheckedRequests.<Build>getRequest(BUILDS).read(createdBuildId.toString()).getStatus();
                    return status.equals("SUCCESS");
                });
    }
}
