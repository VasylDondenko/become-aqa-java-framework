package browsers;

import constants.DriverType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import utils.PropertyUtils;

import java.util.Objects;

public final class DriverManager {
    private static WebDriver driver;

    private static void initializeDriver(ITestContext testContext) {
        String browser = PropertyUtils.get("browser", testContext);
        switch (DriverType.valueOf(browser)) {
            case CHROME:
                System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver");
                driver = new ChromeDriver();
                break;
            case FIREFOX:
                System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/geckodriver");
                driver = new FirefoxDriver();
                break;
            default:
                throw new IllegalStateException("Invalid browser name: " + browser + ". Supported browsers are: CHROME, FIREFOX");
        }
        driver.manage().window().maximize();
    }

    public static WebDriver getDriver(ITestContext testContext) {
        initializeDriver(testContext);
        return driver;
    }

    public static void quitDriver() {
        if (Objects.nonNull(driver)) {
            driver.quit();
        }
    }
}
