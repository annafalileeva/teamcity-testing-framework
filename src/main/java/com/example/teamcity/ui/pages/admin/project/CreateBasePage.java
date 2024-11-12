package com.example.teamcity.ui.pages.admin.project;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.BasePage;

import static com.codeborne.selenide.Selenide.$;

public abstract class CreateBasePage extends BasePage{
    protected static final String CREATE_URL="/admin/createObjectMenu.html?projectId=%s&showMode=%s";

    protected SelenideElement createManuallyButton = $("[href='#createManually']");
    protected SelenideElement urlInput = $("#url");
    protected SelenideElement submitButton = $(Selectors.byAttribute("value", "Proceed"));
    protected SelenideElement buildTypeNameInput = $("#buildTypeName");
    protected SelenideElement connectionSuccessfulMessage = $(".connectionSuccessful");
    protected SelenideElement createButton = $(Selectors.byAttribute("value", "Create"));
    public SelenideElement urlError = $("#error_url");
    public SelenideElement buildTypeNameError = $("#error_buildTypeName");

    protected void baseCreateForm(String url) {
        urlInput.val(url);
        submitButton.click();
        connectionSuccessfulMessage.should(Condition.appear, BASE_WAITING);
    }
}
