package pages;

import helpers.StringHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static locators.ChargePaymentPageLocators.*;

public class ChargePaymentPage {

    private final WebDriver driver;

    public ChargePaymentPage(WebDriver driver) {
        this.driver = driver;
    }

    public void checkIfChargePaymentPageIsDisplayed() {
        WebElement payLogoText = driver.findElement(PAY_LOGO_TEXT.getLocator());
        assertEquals(
                "ОПЛАТА НАЧИСЛЕНИЙ",
                payLogoText.getText(),
                "Некорректно отобразилась форма оплаты"
        );
    }

    public void checkIfAnyFinesFound() {
        String numOfFines = driver.findElement(NUMBER_OF_FINES.getLocator()).getText();
        assertTrue(
                Integer.parseInt(numOfFines) > 0,
                "Должен быть найден хотя бы один штраф"
        );
    }

    public void checkIfPaymentMethodsAreDisplayed() {
        List<WebElement> payMethods = driver.findElements(PAY_METHODS.getLocator());
        List<String> payMethodsNames = payMethods.stream()
                .map(WebElement::getText)
                .toList();
        assertEquals(
                List.of("Банковская карта", "Со счёта телефона", "Yandex Pay"),
                payMethodsNames,
                "Должны быть показаны 3 способа оплаты"
        );
    }

    public void checkFineDocumentFormat() {
        String regNum = driver.findElement(REG_NUM.getLocator()).getText();
        String[] regNumSplitted = regNum.split(" ");
        String regNumActual = regNumSplitted[regNumSplitted.length - 1];

        Pattern pattern = Pattern.compile("^(\\d{1})(.{5})(\\d{4})$");
        Matcher matcher = pattern.matcher(regNumActual);
        matcher.find();

        String actual = matcher.group(1) + "*****" + matcher.group(3);
        String expected = "7*****0343";
        assertEquals(expected, actual, "Документ у штрафа должен совпадать с ожидаемым");
    }

    public void checkIfPaymentEqualsFinePlusCommission() {
        String fine = driver.findElement(FINE.getLocator()).getText();
        String fineSplitted = StringHelper.split(fine, "\\n|\\s", 2);
        float fineNumber = Float.parseFloat(fineSplitted);

        WebElement commission = driver.findElement(COMMISSION.getLocator());
        String commissionValidated = commission.getText().replace(',', '.');
        float commissionNumber = Float.parseFloat(commissionValidated);

        String paymentSum = driver.findElement(PAYMENT_SUM.getLocator()).getText();
        String paymentSumValidated = StringHelper.split(paymentSum, "\\n|\\s", 0).replace(',', '.');
        float paymentNumber = Float.parseFloat(paymentSumValidated);

        assertEquals(
                paymentNumber,
                fineNumber + commissionNumber,
                "Сумма к оплате должна равняться с сумме штрафа + сумма комиссии"
        );
    }

    public void selectPhonePaymentMethod() {
        WebElement mobilePayment = driver.findElement(MOBILE_PAYMENT.getLocator());
        mobilePayment.click();
    }

    public void checkIfPhoneNumberInputIsDisplayed() {
        WebElement phoneNumberInput = driver.findElement(PHONE_NUMBER_INPUT.getLocator());
        assertTrue(
                phoneNumberInput.isDisplayed(),
                "Инпут “Укажите номер, с которого будет списан платёж” должен быть отображен"
        );
    }

    public void checkIfMobileOperatorIconAreDisplayed() {
        List<String> actualClassNames =  driver.findElements(MOBILE_OPERATORS_LOGOS.getLocator())
                .stream()
                .map(e -> e.getAttribute("class"))
                .toList();
        List<String> expectedClassNames = List.of(
                "mobile-pay__logo mobile-pay__logo--beeline",
                "mobile-pay__logo mobile-pay__logo--mts",
                "mobile-pay__logo mobile-pay__logo--megafon",
                "mobile-pay__logo mobile-pay__logo--tele2",
                "mobile-pay__logo mobile-pay__logo--rostelecom"
        );
        assertEquals(
                expectedClassNames,
                actualClassNames,
                "Иконки операторов мобильной связи должны отобразиться"
        );
    }

    public void selectGetBankReceiptOnEmailCheckBox() {
        WebElement getBankReceiptOnEmailCheckBox = driver.findElement(GET_BANK_RECEIPT_ON_EMAIL_CHECKBOX.getLocator());
        getBankReceiptOnEmailCheckBox.click();
    }

    public void checkIfBankReceiptFeeIsDisplayedCorrectly(String expectedFee) {
        String bankReceiptFee = driver.findElement(BANK_RECEIPT_FEE.getLocator()).getText();
        assertEquals(expectedFee, bankReceiptFee, "Сумма за оплату квитанции должна составлять " + expectedFee);
    }

    public void checkIfPaymentEqualsFinePlusBankReceiptFee() {
        String paymentSum = driver.findElement(PAYMENT_SUM.getLocator()).getText();
        String paymentSumSplitted = StringHelper.split(paymentSum, "\\n|\\s", 0);

        String fine = driver.findElement(FINE.getLocator()).getText();
        String fineSplitted = StringHelper.split(fine, "\\n|\\s", 2);

        String bankReceiptFee = driver.findElement(BANK_RECEIPT_FEE.getLocator()).getText();
        String bankReceiptFeeSplitted = StringHelper.split(bankReceiptFee, "\\s|\\+", 1);

        assertEquals(
                Integer.valueOf(paymentSumSplitted),
                Integer.parseInt(bankReceiptFeeSplitted) + Integer.parseInt(fineSplitted),
                "Сумма к оплате должна равняться сумме штрафа + сумма квитанции"
        );
    }

}
