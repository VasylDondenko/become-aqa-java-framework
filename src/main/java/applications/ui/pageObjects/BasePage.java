package applications.ui.pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import utils.TestContextUtils;
import utils.TimeoutUtils;

import java.time.Duration;

public class BasePage {
    private static final Logger logger = LogManager.getLogger(BasePage.class);
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected ITestContext testContext;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(TimeoutUtils.getTimeOut()));
        testContext = TestContextUtils.getContext();
    }

    protected String getTitle() {
        String title = driver.getTitle();
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
