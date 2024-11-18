package com.example.teamcity.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.example.teamcity.BaseTest;
import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.models.User;
import com.example.teamcity.ui.pages.LoginPage;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import java.util.Map;

import static com.example.teamcity.api.enums.Endpoint.USERS;

public class BaseUITest  extends BaseTest {
    @BeforeSuite(alwaysRun = true)
    public void setupUITest() {
        Configuration.browser = Config.getProperty("browser");
        Configuration.baseUrl = "http://" + Config.getProperty("host");
        // НЕ ПИШИТЕ UI ТЕСТЫ С ЛОКАЛЬНЫМ БРАУЗЕРОМ
        // НУЖНО ПИСАТЬ СРАЗУ НА REMOTE BROWSER
        // ТЕСТЫ БУДУТ ЗАПУСКАТЬСЯ НА CI/CD, ТО ЧТО РАБОТАЕТ ЛОКАЛЬНО, МОЖЕТ НЕ РАБОТАТЬ REMOTE
        Configuration.remote = Config.getProperty("remote");
        Configuration.browserSize = Config.getProperty("browserSize");
        Configuration.browserCapabilities.setCapability("selenoid:options",
                Map.of("enableVNC", true, "enableLog", true));
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
                .includeSelenideSteps(true));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        Selenide.closeWebDriver();
    }

    protected void loginAs(User user) {
        superUserCheckRequests.getRequest(USERS).create(user);
        LoginPage.open().login(user);
    }
}