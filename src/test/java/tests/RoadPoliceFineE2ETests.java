package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.MainPage;

import static io.qameta.allure.Allure.step;

@Tag("Chromium")
@Feature("Оплата штрафа ГИБДД")
public class RoadPoliceFineE2ETests extends BaseE2ETest{

    @BeforeEach
    void setUpEach() {
        step("Подготовка окружения", () -> {
            driver = WebDriverManager.chromiumdriver().create();
            mainPage = new MainPage(driver);
        });
    }

    @AfterEach
    void teardownEach() {
        driver.quit();
    }

    @ParameterizedTest
    @Tag("Штрафы")
    @Story("Успешный поиск штрафа ГИБДД")
    @DisplayName("Успешный поиск штрафа ГИБДД")
    @Description("Пользователь выполняет поиск штрафа по номеру свидетельства о регистрации ТС")
    @CsvSource({"7813690343"})
    public void shouldFindRoadPoliceFine(String registrationNumber) {
        Allure.parameter("Cвидетельство о регистрации ТС", registrationNumber);

        step("Перейти на главную страницу", () -> {
            step("Перейти на страницу https://oplatagosuslug.ru/", () -> {
                mainPage.open();
            });
            step("Корректно отобразилась главная страница (логотип)", () -> {
                mainPage.checkIfLogoIsDisplayed();
            });
            step("Отобразилось меню “Выбрать услугу”", () -> {
                mainPage.checkIfChooseServiceMenuIsDisplayed();
            });
        });

        step("Нажать на меню “Выбрать услугу”", () -> {
            step("Нажать на меню", () -> mainPage.clickChooseServiceMenu());
            step("Отобразился список услуг, в том числе пункт “Штрафы ГИБДД”", () -> {
                mainPage.checkIfServicesListAreDisplayed();
                mainPage.checkIfRoadPoliceFinesMenuIsDisplayed();
            });
        });

        step("Нажать на пункт “Штрафы ГИБДД”", () -> {
            step("Нажать на пункт", () -> {
                finesPage = mainPage.clickRoadPoliceFinesMenu();
            });
            step("Корректно отобразилась страница поиска штрафов", () -> {
                finesPage.checkIfHeaderIsDisplayed();
            });
        });

        step("Открыть вкладку “Поиск по документам”", () -> {
            step("Открыть вкладку", () -> {
                documentSearchTabPane = finesPage.clickOnDocumentsSearchTabPane();
            });
            step("Корректно отобразится форма поиска штрафов", () -> {
                documentSearchTabPane.checkIfFinesSearchFormIsDisplayed();
            });
        });

        step("Поиск штрафов", () -> {
            step("Ввести номер Свид. о регистрации ТС", () -> {
                documentSearchTabPane.enterRegistrationNumber(registrationNumber);
            });
            step("Нажать кнопку “Найти штрафы”", () -> {
                paymentPage = documentSearchTabPane.clickFindFinesButton();
            });
            step("Отобразился текст “Найдено начислений”", () -> {
                paymentPage.checkIfChargePaymentPageIsDisplayed();
                paymentPage.checkIfAnyFinesFound();
            });
            step("Показаны 3 способа оплаты (Банковская карта, Со счета телефона, Yandex Pay)", () -> {
                paymentPage.checkIfPaymentMethodsAreDisplayed();
            });
            step("Документ у штрафа совпадает с ожидаемым", () -> {
                paymentPage.checkFineDocumentFormat();
            });
        });

        step("Проверка суммы “К оплате”", () -> {
            step("Сумма к оплате совпадает с суммой штрафа + сумма комиссии", () -> {
                paymentPage.checkIfPaymentEqualsFinePlusCommission();
            });
        });
    }

    @ParameterizedTest
    @Tag("Штрафы")
    @Story("Добавление квитанции при оплате штрафа со счёта телефона")
    @DisplayName("Добавление квитанции при оплате штрафа со счёта телефона")
    @Description("Пользователь проверяет добавление квитанции при оплате штрафа со счёта телефона. " +
            "Поиск штрафа выполняется по номеру свидетельства о регистрации ТС")
    @CsvSource({"7813690343,+35 руб."})
    public void shouldAddBankReceiptWhenPhonePaymentMethodIsUsed(String registrationNumber, String bankReceiptFee) {
        Allure.parameter("Cвидетельство о регистрации ТС", registrationNumber);
        Allure.parameter("Сумма за оплату квитанции", bankReceiptFee);

        step("Предусловие: найти неоплаченный штраф", () -> {
            shouldFindRoadPoliceFine(registrationNumber);
        });

        step(" Выбрать способ оплаты “Со счёта телефона”", () -> {
            step("Выбрать способ отплаты", () -> {
                paymentPage.selectPhonePaymentMethod();
            });
            step("Отобразится инпут “Укажите номер, с которого будет списан платёж”", () -> {
                paymentPage.checkIfPhoneNumberInputIsDisplayed();
            });
            step("Отобразятся иконки операторов мобильной связи", () -> {
                paymentPage.checkIfMobileOperatorIconAreDisplayed();
            });
        });

        step("Нажать чекбокс “получить банковскую квитанцию на e‑mail”", () -> {
            step("Нажать чекбокс", () -> {
                paymentPage.selectGetBankReceiptOnEmailCheckBox();
            });
            step("Сумма за оплату квитанции отображена корректно", () -> {
                paymentPage.checkIfBankReceiptFeeIsDisplayedCorrectly(bankReceiptFee);
            });
        });

        step("Проверка суммы “Итого без учёта комиссии” ", () -> {
            step("Сумма к оплате совпадает с суммой штрафа + сумма за оплату квитанции", () -> {
                paymentPage.checkIfPaymentEqualsFinePlusBankReceiptFee();
            });
        });

    }

}
