package com.example.teamcity;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.AuthSettings;
import com.example.teamcity.api.models.TestData;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import static com.example.teamcity.api.enums.Endpoint.AUTH_SETTINGS;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;


public class BaseTest {
    protected SoftAssert softy;
    protected CheckedRequests superUserCheckRequests = new CheckedRequests(Specifications.superUserSpec());
    protected TestData testData;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        var authSettings = superUserCheckRequests.<AuthSettings>getRequest(AUTH_SETTINGS).readSettings();
        authSettings.setPerProjectPermissions(true);
        superUserCheckRequests.<AuthSettings>getRequest(AUTH_SETTINGS).updateSettings(authSettings);
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        softy = new SoftAssert();
        testData = generate();
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest() {
        softy.assertAll();
        TestDataStorage.getStorage().deleteCreatedEntities();
    }
}
