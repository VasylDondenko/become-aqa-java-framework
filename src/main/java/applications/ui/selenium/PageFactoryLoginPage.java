package applications.ui.selenium;

import browsers.selenium.DriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public final class PageFactoryLoginPage extends BasePage {

    @FindBy(id = "login_field") WebElement fldUsername;
    @FindBy(id = "password") WebElement fldPassword;
    @FindBy(how = How.NAME, using = "commit") WebElement btnCommit;


    public PageFactoryLoginPage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }


    public void goToLoginPage() {
        goTo("/login");
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
