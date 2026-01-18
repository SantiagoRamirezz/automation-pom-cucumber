package com.automation.base.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.util.List;

public class AbstractPage extends PageObject{

    public enum WaitSize {
        XS(500),
        S(3000),
        M(6000),
        L(10000),
        XL(15000);

        private final int millis;

        WaitSize(int millis) {
            this.millis = millis;
        }

        public int millis() {
            return millis;
        }
    }

    protected void waitForDisplayed(WebElementFacade element, WaitSize size) {
        element.withTimeoutOf(Duration.ofMillis(size.millis()))
                .waitUntilVisible();
    }

    protected void waitForNotDisplayed(WebElementFacade element, WaitSize size) {
        try {
            element.withTimeoutOf(Duration.ofMillis(size.millis()))
                    .waitUntilNotVisible();
        } catch (Exception e) {
            throw new AssertionError(
                    "Element was still visible after waiting "
                            + size + " (" + size.millis() + " ms)", e
            );
        }
    }

    protected void selectFromListByText(List<WebElementFacade> elements, String visibleText) {
        for (WebElementFacade element : elements) {
            if (element.getText().trim().equalsIgnoreCase(visibleText.trim())) {
                element.waitUntilClickable().click();
                return;
            }
        }
        throw new AssertionError("Option with text '" + visibleText + "' not found in the list.");
    }

    protected void click(WebElementFacade element) {
        try {
            element.withTimeoutOf(Duration.ofMillis(WaitSize.M.millis()))
                    .waitUntilClickable()
                    .click();
        } catch (Exception e) {
            throw new AssertionError(
                    "Element '" + element.getText() + "' was not clickable after waiting "
                            + WaitSize.M + " (" + WaitSize.M.millis() + " ms)", e
            );
        }
    }

    protected void write(WebElementFacade element, String text) {
        try {
            element.withTimeoutOf(Duration.ofMillis(WaitSize.M.millis()))
                    .waitUntilVisible();

            element.click();
            element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            element.sendKeys(Keys.BACK_SPACE);
            element.sendKeys(text);
        } catch (Exception e) {
            throw new AssertionError(
                    "Element was not visible to write after waiting "
                            + WaitSize.M + " (" + WaitSize.M.millis() + " ms)", e
            );
        }
    }

    protected void writeInputByLabel(List<WebElementFacade> containers, String labelText, String value) {
        for (WebElementFacade container : containers) {
            String label = container.getText().trim();
            if (label.equalsIgnoreCase(labelText.trim())) {
                WebElementFacade input = container.find(By.tagName("input"));
                if (input != null) {
                    write(input, value);
                    return;
                } else {
                    throw new AssertionError("Input not found for container with label: " + labelText);
                }
            }
        }
        throw new AssertionError("No container found with label: " + labelText);
    }


    protected void printTextOfElements(List<WebElementFacade> elements) {
        System.out.println("=== Printing elements text ===");
        if (elements.isEmpty()) {
            System.out.println("The list is empty!");
            return;
        }

        for (int i = 0; i < elements.size(); i++) {
            try {
                String text = elements.get(i).getText().trim();
                System.out.println("Element " + i + ": '" + text + "'");
            } catch (Exception e) {
                System.out.println("Element " + i + ": could not retrieve text");
            }
        }
        System.out.println("=== End of elements ===");
    }

}
