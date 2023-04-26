package browsers;

import constants.DriverType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import utils.PropertyUtils;

public final class DriverManager {
    private static WebDriver driver;

    private static void initializeDriver() {
        String browser = PropertyUtils.get("browser");
        switch (DriverType.valueOf(browser)) {
            case CHROME:
                //TODO move browsers to different classes; Try to use WDB; Try different properties for browsers with some flag.
                System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver");
                driver = new ChromeDriver();
                break;
            case FIREFOX:
                //TODO move browsers to different classes; Try to use WDB;
                System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/geckodriver");
                driver = new FirefoxDriver();
                break;
            default:
                //TODO Take all browsers names from enum
                throw new IllegalStateException("Invalid browser name: " + browser + ". Supported browsers are: CHROME, FIREFOX");
        }
        driver.manage().window().maximize();
    }

    public static WebDriver getDriver() {
        initializeDriver();
        return driver;
    }
}
