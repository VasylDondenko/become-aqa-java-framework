package ui.selenium.github;

import applications.ui.selenium.HomePage;
import applications.ui.selenium.LoginPage;
import applications.ui.selenium.PageFactoryLoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui.selenium.BaseTest;
import utils.PropertyUtils;

public class LoginTests extends BaseTest {


    @Test
    public void testSuccesfullLoginPOM() {

        new LoginPage()
                .goToLoginPage()
                .setUsername(PropertyUtils.get("github.username"))
                .setPassword(PropertyUtils.get("github.password"))
                .clickCommit();

        HomePage homePage = new HomePage()
                .clickMenuBtn();

        Assert.assertEquals(homePage.getUserName(), PropertyUtils.get("github.username"));
    }

    @Test
    public void testSignOut() {
        new LoginPage()
                .goToLoginPage()
                .setUsername(PropertyUtils.get("github.username"))
                .setPassword(PropertyUtils.get("github.password"))
                .clickCommit();

        HomePage homePage = new HomePage();
        homePage.clickMenuBtn();

        Assert.assertEquals(new HomePage().getUserName(), PropertyUtils.get("github.username"));

        homePage.clickSignOunBtn();

        Assert.assertEquals(homePage.getTitle(), "GitHub: Let’s build from here · GitHub");
    }

    @Test
    public void testSuccessfulLoginPageFactory() {
        PageFactoryLoginPage pageFactoryLoginPage = new PageFactoryLoginPage();
        pageFactoryLoginPage.goToLoginPage();
        pageFactoryLoginPage.setUsername(PropertyUtils.get("github.username"));
        pageFactoryLoginPage.setPassword(PropertyUtils.get("github.password"));
        pageFactoryLoginPage.clickCommit();

        HomePage homePage = new HomePage()
                .clickMenuBtn();

        Assert.assertEquals(homePage.getUserName(), PropertyUtils.get("github.username"));
    }

}
