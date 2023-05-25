package applications.ui.selenium;

import browsers.selenium.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.PropertyUtils;
import utils.TimeoutUtils;

import java.time.Duration;

public class BasePage {
    private static final Logger logger = LogManager.getLogger(BasePage.class);
    protected WebDriverWait wait;

    public BasePage() {
        wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TimeoutUtils.getTimeOut()));
    }

    protected void goTo(String endpoint) {
        DriverManager.getDriver().get(PropertyUtils.get("github.ui.baseUrl") + endpoint);
    }

    protected String getTitle() {
        String title = DriverManager.getDriver().getTitle();
        logger.info("Page title is: {}", title);
        return title;
    }

    protected void click(By by, String elementName) {
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
        logger.info("{} is clicked.", elementName);
    }

    protected void setText(By by, String value, String elementName) {
        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        e.clear();
        e.sendKeys(value);
        logger.info("{} is set with value: {}", elementName, value);
    }

    protected String getText(By by, String elementName) {
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getText();
        logger.info("{} is: '{}'", elementName, text);
        return text;
    }
}
