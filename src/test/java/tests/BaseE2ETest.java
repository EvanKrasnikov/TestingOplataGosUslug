package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import org.openqa.selenium.WebDriver;
import pages.ChargePaymentPage;
import pages.DocumentSearchTabPane;
import pages.MainPage;
import pages.RoadPoliceFinesPage;

@Owner("John Doe")
@Epic("E2E Тесты")
@Link(name = "Сайт Оплата госуслуг", url = "https://oplatagosuslug.ru")
public abstract class BaseE2ETest {

    protected WebDriver driver;
    protected MainPage mainPage;
    protected RoadPoliceFinesPage finesPage;
    protected DocumentSearchTabPane documentSearchTabPane;
    protected ChargePaymentPage paymentPage;

}
