package ui;

import applications.ui.pageObjects.HomePage;
import applications.ui.pageObjects.LoginPage;
import applications.ui.pageObjects.PageFactoryLoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.PropertyUtils;

public class LoginTests extends BaseUiTest {

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
