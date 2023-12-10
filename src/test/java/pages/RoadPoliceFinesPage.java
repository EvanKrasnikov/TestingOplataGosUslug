package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static locators.RoadPoliceFinesPageLocators.DOCUMENT_SEARCH_TAB_PANE;
import static locators.RoadPoliceFinesPageLocators.HEADER_TEXT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoadPoliceFinesPage {

    private final WebDriver driver;

    public RoadPoliceFinesPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        String url = "https://oplatagosuslug.ru/shtrafy_gibdd/";
        driver.get(url);
    }

    public void checkIfHeaderIsDisplayed() {
        WebElement headerText = driver.findElement(HEADER_TEXT.getLocator());
        assertEquals(
                "Проверка и оплата штрафов ГИБДД",
                headerText.getText(),
                "Некорректно отобразилась страница поиска штрафов"
        );
    }

    public DocumentSearchTabPane clickOnDocumentsSearchTabPane() {
        WebElement documentSearchTabPane = driver.findElement(DOCUMENT_SEARCH_TAB_PANE.getLocator());
        documentSearchTabPane.click();
        return new DocumentSearchTabPane(driver);
    }

}
