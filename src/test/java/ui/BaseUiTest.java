package ui;

import base.BaseTest;
import browsers.Driver;
import browsers.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.PropertyUtils;

    //TODO Research Best Practices to use BaseTests for different testing types
public class BaseUiTest extends BaseTest {

    @BeforeMethod
    public void setUp() {
        Driver.initDriver(PropertyUtils.get("browser"));
    }

    @AfterMethod
    public void quitDriver() {
        Driver.quitDriver();
    }
}
