package locators;

import org.openqa.selenium.By;

public enum ChargePaymentPageLocators {

    PAY_LOGO_TEXT(By.xpath("//h1/abbr")),
    PAY_METHODS(By.xpath("//article[1]//ul[@class='pay-tabs__list']/li/a")),
    REG_NUM(By.xpath("//div[@id='panel-b1']//dl/dd[2]/pre")),
    FINE(By.xpath("//span[@class='nobr'][1]")),
    COMMISSION(By.xpath("//div[contains(@class, 'commission--card')]//span[@class='commission__value']")),
    PAYMENT_SUM(By.xpath("//p[@class='pm-summ__value']")),
    MOBILE_PAYMENT(By.xpath("//a[@href='#pm-mob1']")),
    PHONE_NUMBER_INPUT(By.xpath("//input[@id='pm-phone']")),
    MOBILE_OPERATORS_LOGOS(By.xpath("//article[1]//ul[@class='mobile-pay__logos']/li")),
    GET_BANK_RECEIPT_ON_EMAIL_CHECKBOX(By.xpath("//div[@class='control-wrapper']//label[contains(@class, 'checkbox')]")),
    BANK_RECEIPT_FEE(By.xpath("//span[@class='strike-through']")),
    NUMBER_OF_FINES(By.xpath("//div[@id='content-zone']/p/strong"));

    private final By locator;

    ChargePaymentPageLocators(By by) {
        this.locator = by;
    }

    public By getLocator() {
        return locator;
    }

}
