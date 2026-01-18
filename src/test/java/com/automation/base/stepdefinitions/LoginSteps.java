package com.automation.base.stepdefinitions;

import com.automation.base.pages.DashboardPage;
import com.automation.base.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.fluentlenium.core.annotation.Page;

public class LoginSteps {

    @Page
    LoginPage loginPage;

    @Page
    DashboardPage dashboardPage;

    @Given("the user opens the login page")
    public void openLoginPage() {
        loginPage.open();
    }

    @Given("the user is logged in")
    public void userIsLoggedIn() {
        loginPage.openLoginPage();
        loginPage.loginAsAdmin();
        dashboardPage.waitUntilPageIsVisible();
    }

    @When("the user logs in with username {string} and password {string}")
    public void login(String user, String password) {
        loginPage.login(user, password);
    }
}
