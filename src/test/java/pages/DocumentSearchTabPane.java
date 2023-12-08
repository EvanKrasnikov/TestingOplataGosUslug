package pages;

import helpers.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static locators.DocumentSearchTabPaneLocators.*;

public class DocumentSearchTabPane {

    private final WebDriver driver;

    public DocumentSearchTabPane(WebDriver driver) {
        this.driver = driver;
    }

    public void checkIfFinesSearchFormIsDisplayed() {
        WebElement regNumInput = driver.findElement(REG_NUM_INPUT.getLocator());
        assertTrue(regNumInput.isDisplayed(), "Инпут Свид. о регистрации ТС должен быть отображен");
        WebElement driverIdInput = driver.findElement(DRIVER_ID_INPUT.getLocator());
        assertTrue(driverIdInput.isDisplayed(), "Инпут Водительское удостоверение должен быть отображен");
    }

    public void enterRegistrationNumber() {
        WebElement regNumInput = driver.findElement(REG_NUM_INPUT.getLocator());
        regNumInput.clear();
        regNumInput.sendKeys("7813690343");
    }

    public ChargePaymentPage clickFindFinesButton() {
        WebElement submitButton = driver.findElement(SUBMIT_BUTTON.getLocator());
        assertTrue(submitButton.isDisplayed(), "Кнопка 'Найти штрафы' должна быть отображена");
        submitButton.click();
        WaitHelper.wait(driver, By.xpath("//h1/abbr"));
        return new ChargePaymentPage(driver);
    }



}
