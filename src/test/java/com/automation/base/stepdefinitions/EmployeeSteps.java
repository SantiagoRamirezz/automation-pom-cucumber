package com.automation.base.stepdefinitions;

import com.automation.base.pages.DashboardPage;
import com.automation.base.pages.PIMPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.fluentlenium.core.annotation.Page;

public class EmployeeSteps {

    private String employeeId;

    private String generateRandomEmployeeId() {
        int random = 100000 + (int) (Math.random() * 900000);
        return String.valueOf(random);
    }

    @Page
    DashboardPage dashboardPage;

    @Page
    PIMPage pimPage;

    @When("the user adds a new employee with login details")
    public void userAddsNewEmployeeWithLoginDetails() {
        employeeId = generateRandomEmployeeId();
        pimPage.fillEmployeeBasicInfo("Santiago", "Andres", "Medina", employeeId);
        pimPage.fillLoginDetails("username"+employeeId, "Colombia1a@");
    }

    @Then("the new employee should appear in the employee list")
    public void newEmployeeShouldAppearInList() {
        dashboardPage.selectSidebarOption("PIM");
        pimPage.searchEmployee("Santiago Andres","Medina",employeeId);
    }
}
