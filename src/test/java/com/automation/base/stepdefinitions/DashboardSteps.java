package com.automation.base.stepdefinitions;

import com.automation.base.pages.DashboardPage;
import com.automation.base.pages.PIMPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.fluentlenium.core.annotation.Page;

public class DashboardSteps {

    @Page
    DashboardPage dashboardPage;

    @Page
    PIMPage pimPage;

    @When("the user navigates to the PIM module")
    public void userNavigatesToPIMModule() {
        dashboardPage.selectSidebarOption("PIM");
        pimPage.waitUntilPageIsVisible();
    }

    @Then("the user should see the dashboard page")
    public void shouldSeeDashboard() {
        dashboardPage.waitUntilPageIsVisible();
    }
}
