package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static locators.MainPageLocators.*;
import static org.junit.jupiter.api.Assertions.*;

public class MainPage {

    private final WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        String URL = "https://oplatagosuslug.ru/";
        driver.get(URL);
    }

    public void checkIfLogoIsDisplayed() {
        WebElement logoImage = driver.findElement(LOGO_IMAGE.getLocator());
        assertTrue(
                logoImage.isDisplayed(),
                "Логотип главной страницы должен отображаться корректно"
        );

        WebElement logoText = driver.findElement(LOGO_TEXT.getLocator());
        assertEquals(
                "Оплата госуслуг",
                logoText.getText(),
                "Текст логотипа главной страницы должен отображаться корректно"
        );
    }

    public void checkIfChooseServiceMenuIsDisplayed() {
        WebElement chooseServiceMenu = driver.findElement(CHOOSE_SERVICE_MENU.getLocator());
        assertTrue(chooseServiceMenu.isDisplayed(), "Меню 'Выбрать услугу' должно отображаться");
    }

    public void clickChooseServiceMenu() {
        WebElement chooseServiceMenu = driver.findElement(CHOOSE_SERVICE_MENU.getLocator());
        chooseServiceMenu.click();
    }

    public void checkIfServicesListAreDisplayed() {
        List<WebElement> services = driver.findElements(SERVICES_LIST.getLocator());
        assertFalse(services.isEmpty(), "Список услуг не должен быть пустым");
    }

    public void checkIfRoadPoliceFinesMenuIsDisplayed() {
        WebElement roadPoliceFinesMenu = driver.findElement(ROAD_POLICE_FINES_MENU.getLocator());
        assertTrue(roadPoliceFinesMenu.isDisplayed(), "Пункт Штрафы ГИБДД должен отобразиться");
    }

    public RoadPoliceFinesPage clickRoadPoliceFinesMenu() {
        WebElement roadPoliceFinesMenu = driver.findElement(ROAD_POLICE_FINES_MENU.getLocator());
        roadPoliceFinesMenu.click();
        return new RoadPoliceFinesPage(driver);
    }

}