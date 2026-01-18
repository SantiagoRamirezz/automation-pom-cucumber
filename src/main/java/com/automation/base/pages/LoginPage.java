package com.automation.base.pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

@DefaultUrl("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login")
public class LoginPage extends AbstractPage {

    @FindBy(name = "username")
    WebElementFacade username;

    @FindBy(name = "password")
    WebElementFacade password;

    @FindBy(css = "button[type='submit']")
    WebElementFacade loginButton;

    public void openLoginPage() {
        open();
    }

    public void login(String user, String pass) {
        username.type(user);
        password.type(pass);
        loginButton.waitUntilClickable().click();
    }

    public void loginAsAdmin() {
        username.type("Admin");
        password.type("admin123");
        loginButton.waitUntilClickable().click();
    }
}
