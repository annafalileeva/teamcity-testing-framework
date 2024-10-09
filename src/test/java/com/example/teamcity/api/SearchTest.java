package com.example.teamcity.api;

import com.example.teamcity.api.models.*;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.*;
import static com.example.teamcity.api.enums.Locator.ID;
import static com.example.teamcity.api.enums.Locator.NAME;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;
import static io.qameta.allure.Allure.step;

@Test(groups = {"Regression"})
public class SearchTest extends BaseApiTest{
    @Test(description = "User should be able to search project by name", groups = {"Positive", "Search"})
    public void userSearchesProjectByNameTest() {
        step("Create user");
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        step("Create project1");
        userCheckRequests.getRequest(PROJECTS).create(testData.getProject());

        step("Create project2");
        userCheckRequests.getRequest(PROJECTS).create(generate(Project.class));

        step("Search project1 by name");
        var foundProjests = userCheckRequests.<ProjectList>getRequest(PROJECTS).search(NAME, testData.getProject().getName());

        step("Check that project1 in search results");
        softy.assertEquals(foundProjests.getCount(), 1);
        softy.assertEquals(foundProjests.getProject().get(0).getId(), testData.getProject().getId(),
                "Incorrent project in search results");
    }

    @Test(description = "User should be able to search user by id", groups = {"Positive", "Search"})
    public void userSearchesUserByIdTest() {
        step("Create user");
        superUserCheckRequests.<User>getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        step("Create user for search");
        var userForSearch = superUserCheckRequests.<User>getRequest(USERS).create(generate(User.class));

        step("Search user by id");
        var foundUser = userCheckRequests.<UserList>getRequest(USERS).search(ID, userForSearch.getId());

        step("Check that user in search results");
        softy.assertEquals(foundUser.getCount(), 1);
        softy.assertEquals(foundUser.getUser().get(0).getUsername(), userForSearch.getUsername().toLowerCase(),
                "Incorrent user in search results");
    }
}
