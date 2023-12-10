package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.ChargePaymentPage;
import pages.RoadPoliceFinesPage;

import static io.qameta.allure.Allure.step;

public class DocumentSearchFormTests extends BaseE2ETest {

    @BeforeEach
    void setUpEach() {
        step("Подготовка окружения", () -> {
            driver = WebDriverManager.chromiumdriver().create();
        });

        step("Открытие вкладки Поиск по документам", () -> {
            RoadPoliceFinesPage finesPage = new RoadPoliceFinesPage(driver);
            finesPage.open();
            documentSearchTabPane = finesPage.clickOnDocumentsSearchTabPane();
            documentSearchTabPane.checkIfFinesSearchFormIsDisplayed();
        });
    }

    @AfterEach
    void teardownEach() {
//        driver.quit();
    }

    @ParameterizedTest()
    @DisplayName("Валидное Свид. о регистрации ТС и валидное Водительское удостоверение")
//    @CsvSource({"7813690343,1111111111,false"})
    @CsvSource({"7813690343,' ',false"})
    public void testPos(String registrationNumber, String driverId, boolean notifyPayment) {
        enterFormData(registrationNumber, driverId, notifyPayment);
        step("Найден хотя бы 1 штраф", () -> {
            ChargePaymentPage paymentPage = documentSearchTabPane.clickFindFinesButton();
            paymentPage.checkIfChargePaymentPageIsDisplayed();
            paymentPage.checkIfAnyFinesFound();
        });
    }

    @ParameterizedTest()
    @DisplayName("Невалидное Свид. о регистрации ТС и валидное Водительское удостоверение")
    @CsvSource({"AB,1111111111,false"})
    public void testNegRegNum(String registrationNumber, String driverId, boolean notifyPayment) {
        enterFormData(registrationNumber, driverId, notifyPayment);
        step("Проверка валидации инпутов", () -> {
            documentSearchTabPane.checkIfRegNumberInputIsNotValid();
            documentSearchTabPane.checkIfDriverIdInputIsValid();
        });
        checkHeader();
    }

    @ParameterizedTest()
    @DisplayName("Валидное Свид. о регистрации ТС и невалидное Водительское удостоверение")
    @CsvSource({"7813690343,LL,false"})
    public void testNegDriverId(String registrationNumber, String driverId, boolean notifyPayment) {
        enterFormData(registrationNumber, driverId, notifyPayment);
        step("Проверка валидации инпутов", () -> {
            documentSearchTabPane.checkIfRegNumberInputIsValid();
            documentSearchTabPane.checkIfDriverIdInputIsNotValid();
        });
        checkHeader();
    }

    @ParameterizedTest()
    @DisplayName("Невалидное Свид. о регистрации ТС и невалидное Водительское удостоверение")
    @CsvSource({"!(*,AB11111111,false"})
    public void testNegAll(String registrationNumber, String driverId, boolean notifyPayment) {
        enterFormData(registrationNumber, driverId, notifyPayment);
        step("Проверка валидации инпутов", () -> {
            documentSearchTabPane.checkIfRegNumberInputIsNotValid();
            documentSearchTabPane.checkIfDriverIdInputIsNotValid();
        });
        checkHeader();
    }

    @Step("Были введены данные registrationNumber = [{registrationNumber}] driverId = [{driverId}] notifyPayment = [{notifyPayment}]")
    public void enterFormData(String registrationNumber, String driverId, boolean notifyPayment) {
        documentSearchTabPane.enterRegistrationNumber(registrationNumber);
        documentSearchTabPane.enterDriverId(driverId);
        if (notifyPayment) {
            documentSearchTabPane.clickNotifyPayment();
        }
    }

    @Step("Пользователь должен остаться на вкладке Поиск по документам ")
    public void checkHeader() {
        documentSearchTabPane.checkHeader();
    }

}
