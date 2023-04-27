package ui;

import applications.ui.pageObjects.HomePage;
import applications.ui.pageObjects.LoginPage;
import applications.ui.pageObjects.PageFactoryLoginPage;
import configs.UIConfigInitializer;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseUiTest {

    @Test(enabled = false)
    public void testSuccesfullLoginPOM() {
        new LoginPage(driver)
                .setUsername(UIConfigInitializer.getGithubUsername())
                .setPassword(UIConfigInitializer.getGithubPassword())
                .clickCommit();

        HomePage homePage = new HomePage(driver)
                .clickMenuBtn();

        Assert.assertEquals(homePage.getUserName(), "VasylDondenko");
    }

    @Test(enabled = false)
    public void testSignOut() {
        new LoginPage(driver)
                .setUsername(UIConfigInitializer.getGithubUsername())
                .setPassword(UIConfigInitializer.getGithubPassword())
                .clickCommit();

        HomePage homePage = new HomePage(driver);
        homePage.clickMenuBtn();

        Assert.assertEquals(new HomePage(driver).getUserName(), "VasylDondenko");

        homePage.clickSignOunBtn();

        Assert.assertEquals(homePage.getTitle(), "GitHub: Let’s build from here · GitHub");
    }

    @Test(enabled = false)
    public void testSuccessfulLoginPageFactory() {
        PageFactoryLoginPage pageFactoryLoginPage = new PageFactoryLoginPage(driver);
        pageFactoryLoginPage.goToLoginPage();
        pageFactoryLoginPage.setUsername(UIConfigInitializer.getGithubUsername());
        pageFactoryLoginPage.setPassword(UIConfigInitializer.getGithubPassword());
        pageFactoryLoginPage.clickCommit();

        HomePage homePage = new HomePage(driver)
                .clickMenuBtn();

        Assert.assertEquals(homePage.getUserName(), "VasylDondenko");
    }

}
