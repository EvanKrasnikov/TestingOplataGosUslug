package locators;

import org.openqa.selenium.By;

public enum DocumentSearchTabPaneLocators {

    REG_NUM_INPUT(By.xpath("//input[@id='regnum1']")),
    REG_NUM_INPUT_LABEL(By.xpath("//div[@id='regTS-block1']/div[2]")),
    DRIVER_ID_INPUT(By.xpath("//input[@id='driverid1']")),
    DRIVER_ID_INPUT_LABEL(By.xpath("//div[@id='vu-number-block-number']/div[@class='errormark']")),
    SUBMIT_BUTTON(By.xpath("//form[@id='payform-search']//button[@type='submit']")),
    NOTIFY_PAYMENT_CHECKBOX(By.xpath("//input[@class='notifyPayment']")),
    TAB_PANE_HEADER(By.xpath("//a[@id='allDocsTab']/span[2]"));

    private final By locator;

    DocumentSearchTabPaneLocators(By by) {
        this.locator = by;
    }

    public By getLocator() {
        return locator;
    }
}
