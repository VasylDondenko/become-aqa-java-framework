package applications.ui.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.PropertyUtils;

public final class PageFactoryLoginPage extends BasePage {

    @FindBy(id = "login_field") WebElement fldUsername;
    @FindBy(id = "password") WebElement fldPassword;
    @FindBy(how = How.NAME, using = "commit") WebElement btnCommit;


    public PageFactoryLoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    public void goToLoginPage() {
        driver.get(PropertyUtils.get("github.ui.baseUrl") + "/login");
    }

    public void setUsername(String username) {
        WebElement e = wait.until(ExpectedConditions.visibilityOf(fldUsername));
        e.clear();
        e.sendKeys(username);
    }

    public void setPassword(String password) {
        WebElement e = wait.until(ExpectedConditions.visibilityOf(fldPassword));
        e.clear();
        e.sendKeys(password);
    }

    public void clickCommit() {
        wait.until(ExpectedConditions.elementToBeClickable(btnCommit)).click();
    }
}
