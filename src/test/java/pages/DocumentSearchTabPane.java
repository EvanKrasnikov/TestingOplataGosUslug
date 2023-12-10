package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static locators.DocumentSearchTabPaneLocators.*;
import static locators.ChargePaymentPageLocators.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    public void enterRegistrationNumber(String registrationNumber) {
        WebElement regNumInput = driver.findElement(REG_NUM_INPUT.getLocator());
        regNumInput.clear();
        regNumInput.sendKeys(registrationNumber);
//        regNumInput.submit();
    }

    public void checkHeader() {
        String actualHeader = driver.findElement(TAB_PANE_HEADER.getLocator()).getText();
        assertEquals(
                "ПОИСК ПО ДОКУМЕНТАМ",
                actualHeader,
                "Пользователь не находится во вкладке 'ПОИСК ПО ДОКУМЕНТАМ'"
        );
    }

    public void checkIfRegNumberInputIsNotValid() {
//        WebElement element = driver.findElement(REG_NUM_INPUT_LABEL.getLocator());
//        new WebDriverWait(driver, Duration.of(20, ChronoUnit.SECONDS))
//                .until(ExpectedConditions.visibilityOf(element));
        checkIfInputIsNotValid(REG_NUM_INPUT.getLocator(), REG_NUM_INPUT_LABEL.getLocator());
    }

    public void checkIfRegNumberInputIsValid() {
//        WebElement element = driver.findElement(REG_NUM_INPUT_LABEL.getLocator());
//        new WebDriverWait(driver, Duration.of(20, ChronoUnit.SECONDS))
//                .until(ExpectedConditions.visibilityOf(element));
        checkIfInputIsValid(REG_NUM_INPUT.getLocator(), REG_NUM_INPUT_LABEL.getLocator());
    }

    public void checkIfDriverIdInputIsNotValid() {
//        WebElement element = driver.findElement(REG_NUM_INPUT_LABEL.getLocator());
//        new WebDriverWait(driver, Duration.of(20, ChronoUnit.SECONDS))
//                .until(ExpectedConditions.visibilityOf(element));
        checkIfInputIsNotValid(DRIVER_ID_INPUT.getLocator(), DRIVER_ID_INPUT_LABEL.getLocator());
    }

    public void checkIfDriverIdInputIsValid() {
        WebElement element = driver.findElement(REG_NUM_INPUT_LABEL.getLocator());
        new WebDriverWait(driver, Duration.of(20, ChronoUnit.SECONDS))
                .until(ExpectedConditions.visibilityOf(element));
        checkIfInputIsValid(DRIVER_ID_INPUT.getLocator(), DRIVER_ID_INPUT_LABEL.getLocator());
    }

    private void checkIfInputIsNotValid(By inputBy, By labelBy) {
        WebElement regNumInput = driver.findElement(inputBy);
        String shadowColor = regNumInput.getCssValue("box-shadow");
        assertEquals(
                "rgb(246, 142, 142) 0px 0px 1px 2px, rgb(246, 142, 142) 0px 0px 1px 1px",
                shadowColor,
                "Цвет тени инпута неправильный"
        );

        WebElement regNumInputLabel = driver.findElement(labelBy);
        String text = regNumInputLabel.getText();
        assertEquals("Неверный формат", text, "Текст лейбла неверный");
        String opacity = regNumInputLabel.getCssValue("opacity");
        assertEquals("1", opacity, "Цвет надписи должен неправильный");
    }

    private void checkIfInputIsValid(By inputBy, By labelBy) {
        WebElement regNumInput = driver.findElement(inputBy);
        String shadowColor = regNumInput.getCssValue("box-shadow");
        assertEquals(
                "rgba(0, 0, 0, 0.25) 0px 0px 0px 1px inset",
                shadowColor,
                "Цвет тени инпута неправильный"
        );

        WebElement regNumInputLabel = driver.findElement(labelBy);
//        String text = regNumInputLabel.getText();
//        assertEquals("", text, "Текст лейбла неверный");
        String opacity = regNumInputLabel.getCssValue("opacity");
        assertEquals("0", opacity, "Цвет надписи должен неправильный");
    }

    public void enterDriverId(String driverId) {
        WebElement driverIdInput = driver.findElement(DRIVER_ID_INPUT.getLocator());
        driverIdInput.clear();
        driverIdInput.sendKeys(driverId);
//        driverIdInput.submit();
    }

    public void clickNotifyPayment() {
        WebElement notifyPayment = driver.findElement(NOTIFY_PAYMENT_CHECKBOX.getLocator());
        notifyPayment.click();
    }

    public ChargePaymentPage clickFindFinesButton() {
        WebElement submitButton = driver.findElement(SUBMIT_BUTTON.getLocator());
        assertTrue(submitButton.isDisplayed(), "Кнопка 'Найти штрафы' должна быть отображена");
        submitButton.click();

        new WebDriverWait(driver, Duration.of(20, ChronoUnit.SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(PAY_LOGO_TEXT.getLocator()));
//        new WebDriverWait(driver, Duration.of(20, ChronoUnit.SECONDS))
//                .until(driver -> ((JavascriptExecutor) driver)
//                        .executeScript("return document.readyState")
//                        .equals("complete"));
//        new WebDriverWait(driver, Duration.of(20, ChronoUnit.SECONDS))
//                .until(ExpectedConditions.visibilityOfElementLocated(HEADER_TEXT.getLocator()));
        return new ChargePaymentPage(driver);
    }

}
