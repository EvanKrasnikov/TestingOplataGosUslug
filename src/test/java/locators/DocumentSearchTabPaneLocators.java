package locators;

import org.openqa.selenium.By;

public enum DocumentSearchTabPaneLocators {

    REG_NUM_INPUT(By.xpath("//input[@id='regnum1']")),
    DRIVER_ID_INPUT(By.xpath("//input[@id='driverid1']")),
    SUBMIT_BUTTON(By.xpath("//form[@id='payform-search']//button[@type='submit']"));

    private final By locator;

    DocumentSearchTabPaneLocators(By by) {
        this.locator = by;
    }

    public By getLocator() {
        return locator;
    }
}
