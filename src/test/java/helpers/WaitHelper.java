package helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class WaitHelper {

    public static void wait(WebDriver driver, By by) {
        new WebDriverWait(driver, Duration.of(20, ChronoUnit.SECONDS))
                .until(ExpectedConditions.elementToBeClickable(by));
    }

    public static void waitToBeVisible(WebDriver driver, WebElement element) {
        new WebDriverWait(driver, Duration.of(20, ChronoUnit.SECONDS))
                .until(ExpectedConditions.visibilityOf(element));
    }

}
