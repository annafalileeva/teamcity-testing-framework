package com.example.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

@Getter
public class ProjectTreeElement extends BasePageElement {
    private SelenideElement name;

    public ProjectTreeElement(SelenideElement element) {
        super(element);
        this.name = find("span[class*='ProjectsTreeItem__name']");
    }
}
