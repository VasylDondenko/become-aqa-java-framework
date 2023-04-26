package ui;

import api.base.BaseTest;
import browsers.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

//TODO Research Best Practices to use BaseTests for different testing types
public class BaseUiTest extends BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = DriverManager.getDriver();
    }

    @AfterMethod
    public void quitDriver() {
        driver.quit();
    }
}
