package helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class WaitHelper {

    public static void wait(WebDriver driver, By by) {
        new WebDriverWait(driver, Duration.of(20, ChronoUnit.SECONDS))
                .until(ExpectedConditions.elementToBeClickable(by));
    }

}
