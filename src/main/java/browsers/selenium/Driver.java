package browsers.selenium;

import constants.DriverType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Arrays;
import java.util.Objects;

public final class Driver {

    private Driver() {
    }

    public static void initDriver(String browser) {
        if (Objects.isNull(DriverManager.getDriver())) {
            switch (DriverType.valueOf(browser.toUpperCase())) {
                case CHROME:
                    System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver");
                    DriverManager.setDriver(new ChromeDriver());
                    break;
                case FIREFOX:
                    System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/geckodriver");
                    DriverManager.setDriver(new FirefoxDriver());
                    break;
                default:
                    throw new IllegalStateException("Invalid browser name: " + browser + ". Supported browsers are: " +
                            Arrays.toString(Arrays.stream(DriverType.values())
                                    .map(Enum::name)
                                    .toArray(String[]::new)));
            }
            DriverManager.getDriver().manage().window().maximize();
        }
    }

    public static void quitDriver() {
        if (Objects.nonNull(DriverManager.getDriver())) {
            DriverManager.getDriver().quit();
            DriverManager.unload();
        }
    }
}