package locators;

import org.openqa.selenium.By;

public enum MainPageLocators {

    LOGO_IMAGE(By.xpath("//h1/img")),
    LOGO_TEXT(By.xpath("//h1/span")),
    CHOOSE_SERVICE_MENU(By.xpath("//*[@class='header-nav__link']")),
    SERVICES_LIST(By.xpath("//button[@class='header-nav__link']//following-sibling::div//ul[1]")),
    ROAD_POLICE_FINES_MENU(By.xpath("//div[@aria-labelledby='dLabel']//a[@href='/shtrafy_gibdd/']"));

    private final By locator;

    MainPageLocators(By by) {
        this.locator = by;
    }

    public By getLocator() {
        return locator;
    }

}