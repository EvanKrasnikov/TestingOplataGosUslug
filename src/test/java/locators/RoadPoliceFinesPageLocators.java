package locators;

import org.openqa.selenium.By;

public enum RoadPoliceFinesPageLocators {

    HEADER_TEXT(By.xpath("//h1")),
    DOCUMENT_SEARCH_TAB_PANE(By.xpath("//form//li"));

    private final By locator;

    RoadPoliceFinesPageLocators(By by) {
        this.locator = by;
    }

    public By getLocator() {
        return locator;
    }

}
