package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.ChargePaymentPage;
import pages.DocumentSearchTabPane;
import pages.MainPage;
import pages.RoadPoliceFinesPage;

public class RoadPoliceFineE2ETests {

    private WebDriver driver;

    @BeforeEach
    void setUpEach() {
        driver = WebDriverManager.chromiumdriver().create();
    }

    @AfterEach
    void teardownEach() {
        driver.quit();
    }

    @Test
    @DisplayName("Успешный поиск штрафа ГИБДД")
    public void shouldFindRoadPoliceFine() {
        MainPage mainPage = new MainPage(driver);

        // Если перейти на главную страницу https://oplatagosuslug.ru/
        mainPage.open();
        // То корректно отобразится главная страница (логотип)
        mainPage.checkIfLogoIsDisplayed();
        // И отобразится меню “Выбрать услугу”
        mainPage.checkIfChooseServiceMenuIsDisplayed();

        // Если нажать на меню “Выбрать услугу”
        mainPage.clickChooseServiceMenu();
        // То отобразился список услуг, в том числе пункт “Штрафы ГИБДД”
        mainPage.checkIfServicesListAreDisplayed();
        mainPage.checkIfRoadPoliceFinesMenuIsDisplayed();

        // Если нажать на пункт “Штрафы ГИБДД”
        RoadPoliceFinesPage finesPage = mainPage.clickRoadPoliceFinesMenu();
        // То корректно отобразится страница поиска штрафов (https://oplatagosuslug.ru/shtrafy_gibdd/)
        finesPage.checkIfHeaderIsDisplayed();

        // Если открыть вкладку “Поиск по документам”
        DocumentSearchTabPane documentSearchTabPane = finesPage.clickOnDocumentsSearchTabPane();
        // То корректно отобразится форма поиска штрафов (инпуты Свид. о регистрации ТС и Водительское удостоверение)
        documentSearchTabPane.checkIfFinesSearchFormIsDisplayed();

        // Если ввести номер Свид. о регистрации ТС (7813690343)
        documentSearchTabPane.enterRegistrationNumber();
        // И нажать кнопку “Найти штрафы”
        ChargePaymentPage paymentPage = documentSearchTabPane.clickFindFinesButton();
        // То отобразится текст Найдено начислений:
        paymentPage.checkIfChargePaymentPageIsDisplayed();
        // И будут показаны 3 способа оплаты (Банковская карта, Со счета телефона, Yandex Pay)
        paymentPage.checkIfPaymentMethodsAreDisplayed();
        // И документ у штрафа совпадает с ожидаемым (в формате 7*****0343)
        paymentPage.checkFineDocumentFormat();
        // И сумма к оплате совпадает с суммой штрафа (правый верхний угол) + сумма комиссии
        paymentPage.checkIfPaymentEqualsFinePlusCommission();
    }

    @Test
    @DisplayName("Добавление квитанции при оплате штрафа со счёта телефона")
    public void shouldAddBankReceiptWhenPhonePaymentMethodIsUsed() {
        // Дано найти неоплаченный штраф
        shouldFindRoadPoliceFine();
        ChargePaymentPage paymentPage = new ChargePaymentPage(driver);

        // Если выбрать способ оплаты “Со счёта телефона”
        paymentPage.selectPhonePaymentMethod();
        // То отобразится инпут “Укажите номер, с которого будет списан платёж”
        paymentPage.checkIfPhoneNumberInputIsDisplayed();
        // И отобразятся иконки операторов мобильной связи
        paymentPage.checkIfMobileOperatorIconAreDisplayed();

        // Если нажать чекбокс “получить банковскую квитанцию на e‑mail”
        paymentPage.selectGetBankReceiptOnEmailCheckBox();
        // То сумма за оплату квитанции отображена корректно (35р)
        paymentPage.checkIfBankReceiptFeeIsDisplayedCorrectly();
        // И сумма к оплате совпадает с суммой штрафа + сумма за оплату квитанции
        paymentPage.checkIfPaymentEqualsFinePlusBankReceiptFee();
    }

}
