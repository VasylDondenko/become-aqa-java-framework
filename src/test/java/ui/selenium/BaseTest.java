package ui.selenium;

import browsers.selenium.Driver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.PropertyUtils;

    //TODO Research Best Practices to use BaseTests for different testing types
public class BaseTest extends base.BaseTest {

    @BeforeMethod
    public void setUp() {
        Driver.initDriver(PropertyUtils.get("browser"));
    }

    @AfterMethod
    public void quitDriver() {
        Driver.quitDriver();
    }
}
