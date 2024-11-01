package com.example.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

@Getter
public class StepTypeElement extends BasePageElement{
    private SelenideElement name;

    public StepTypeElement(SelenideElement element) {
        super(element);
        this.name = find("td[class*='SelectBuildRunners__title']");
    }
}
