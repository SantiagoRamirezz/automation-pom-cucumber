package com.automation.base.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import java.util.Arrays;
import java.util.List;

public class PIMPage extends AbstractPage {

    @FindBy(css = "div.orangehrm-paper-container")
    WebElementFacade mainContentPanel;

    @FindBy(css = "div.orangehrm-header-container button")
    WebElementFacade addEmployeeButton;

    @FindBy(name = "firstName")
    WebElementFacade firstNameInput;

    @FindBy(name = "middleName")
    WebElementFacade middleNameInput;

    @FindBy(name = "lastName")
    WebElementFacade lastNameInput;

    @FindBy(css = ".user-form-header .oxd-switch-wrapper")
    WebElementFacade detailsSwitch;

    @FindBy(css = ".oxd-input-group")
    private List<WebElementFacade> inputGroups;

    @FindBy(css = ".oxd-form-actions button[type='submit']")
    WebElementFacade employeePrincipalButton;

    @FindBy(css = ".orangehrm-container .oxd-table-body .oxd-table-card")
    private List<WebElementFacade> itemsTable;

    @FindBy(css = ".oxd-toast-container .oxd-toast")
    WebElementFacade greenToast;

    public void waitUntilPageIsVisible() {
        waitForDisplayed(mainContentPanel, WaitSize.M);
    }

    public void fillEmployeeBasicInfo(String firstName, String middleName, String lastName, String employeeId) {
        click(addEmployeeButton);
        write(firstNameInput, firstName);
        write(middleNameInput, middleName);
        write(lastNameInput, lastName);
        printTextOfElements(inputGroups);
        writeInputByLabel(inputGroups, "Employee Id", employeeId);
    }

    public void fillLoginDetails(String userName, String password) {
        click(detailsSwitch);
        writeInputByLabel(inputGroups, "Username",userName);
        writeInputByLabel(inputGroups, "Password",password);
        writeInputByLabel(inputGroups, "Confirm Password",password);
        click(employeePrincipalButton);
        waitForDisplayed(greenToast, WaitSize.L);
        waitForNotDisplayed(greenToast, WaitSize.M);
    }

    public void searchEmployee( String firstName, String lastName, String employeeId) {
        writeInputByLabel(inputGroups, "Employee Id", employeeId);
        click(employeePrincipalButton);
        waitForDisplayed(employeePrincipalButton, WaitSize.M);
        List<Integer> columns = Arrays.asList(1, 2, 3);
        List<String> expectedValues = Arrays.asList(employeeId, firstName, lastName);

        validateSingleRowAndColumns(itemsTable, columns, expectedValues);
    }

    public void validateSingleRowAndColumns(List<WebElementFacade> tableRows, List<Integer> columnsToValidate, List<String> expectedValues) {
        long endTime = System.currentTimeMillis() + WaitSize.L.millis();
        while (System.currentTimeMillis() < endTime) {
            if (tableRows.size() == 1) {
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (tableRows.size() != 1) {
            throw new AssertionError("Expected exactly 1 row, but found: " + tableRows.size());
        }

        WebElementFacade row = tableRows.get(0);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", row);

        List<WebElementFacade> cells = row.thenFindAll(By.cssSelector(".oxd-table-row .oxd-table-cell"));

        if (columnsToValidate.size() != expectedValues.size()) {
            throw new AssertionError("Number of columns to validate and expected values must match.");
        }

        for (int i = 0; i < columnsToValidate.size(); i++) {
            int colIndex = columnsToValidate.get(i);
            String expected = expectedValues.get(i);

            if (colIndex >= cells.size()) {
                throw new AssertionError("Column index " + colIndex + " is out of bounds for row with " + cells.size() + " columns.");
            }

            String actual = cells.get(colIndex).getText().trim();
            if (!actual.equals(expected)) {
                throw new AssertionError("Column " + colIndex + " expected: '" + expected + "' but found: '" + actual + "'");
            }
        }
    }

}
