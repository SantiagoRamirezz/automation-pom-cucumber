package com.automation.base.pages;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.annotations.findby.FindBy;

import java.util.List;

public class DashboardPage extends AbstractPage {

    @FindBy(css = "header.oxd-topbar")
    WebElementFacade dashboardHeader;

    @FindBy(css = "nav.oxd-navbar-nav")
    WebElementFacade dashboardNavBar;

    @FindBy(css = "ul.oxd-main-menu li > a")
    private List<WebElementFacade> sidebarOptions;

    public void waitUntilPageIsVisible() {
        waitForDisplayed(dashboardHeader, WaitSize.M);
        waitForDisplayed(dashboardNavBar, WaitSize.M);
    }

    public void selectSidebarOption(String optionText) {
        selectFromListByText(sidebarOptions, optionText);
    }
}
