package applications.ui.pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.PropertyUtils;

public final class LoginPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(BasePage.class);

    private final By fldUsername = new By.ById("login_field");
    private final By fldPassword = new By.ById("password");
    private final By btnCommit = new By.ByName("commit");

    public LoginPage(WebDriver driver) {
        super(driver);
        driver.get(PropertyUtils.get("github.ui.baseUrl", testContext) + "/login");
    }


    public LoginPage setUsername(String username) {
        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(fldUsername));
        e.clear();
        e.sendKeys(username);
        logger.info("{} is set with value: {}", "Username field is set with value: ", username);
        return this;
    }

    public LoginPage setPassword(String password) {
        setText(fldPassword, password, "Password field");
        return this;
    }

    public void clickCommit() {
        click(btnCommit, "Commit button");
    }
}
