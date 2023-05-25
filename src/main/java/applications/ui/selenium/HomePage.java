package applications.ui.selenium;

import org.openqa.selenium.By;

public final class HomePage extends BasePage {
    private final By btnMenu = new By.ByXPath("(//summary[@class='Header-link'])[2]");
    private final By btnSignOut = new By.ByCssSelector(".dropdown-signout");
    private final By signedInUsername = new By.ByXPath("//strong[@class='css-truncate-target']");


    public String getTitle() {
        return super.getTitle();
    }

    public HomePage clickMenuBtn() {
        click(btnMenu, "Menu button");
        return this;
    }

    public void clickSignOunBtn() {
        click(btnSignOut, "Sign out button");
    }

    public String getUserName() {
        return getText(signedInUsername, "Signed in username");
    }
}
