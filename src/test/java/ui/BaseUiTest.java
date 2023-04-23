package ui;

import api.base.BaseTest;
import browsers.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public class BaseUiTest extends BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp(ITestContext context) {
        driver = DriverManager.getDriver(context);
    }

    @AfterMethod
    public void quitDriver() {
        DriverManager.quitDriver();
    }
}
