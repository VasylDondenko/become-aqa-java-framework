package ui;

import applications.ui.pageObjects.HomePage;
import applications.ui.pageObjects.LoginPage;
import applications.ui.pageObjects.PageFactoryLoginPage;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import utils.PropertyUtils;

public class LoginTests extends BaseUiTest {

    @Test
    public void testSuccesfullLoginPOM(ITestContext testContext) {
        new LoginPage(driver)
                .setUsername(PropertyUtils.get("github.username", testContext))
                .setPassword(PropertyUtils.get("github.password", testContext))
                .clickCommit();

        HomePage homePage = new HomePage(driver)
                .clickMenuBtn();

        Assert.assertEquals(homePage.getUserName(), "VasylDondenko");
    }

    @Test
    public void testSignOut(ITestContext testContext) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setUsername(PropertyUtils.get("github.username", testContext));
        loginPage.setPassword(PropertyUtils.get("github.password", testContext));
        loginPage.clickCommit();

        HomePage homePage = new HomePage(driver);
        homePage.clickMenuBtn();
        Assert.assertEquals(new HomePage(driver).getUserName(), "VasylDondenko");
        homePage.clickSignOunBtn();
        Assert.assertEquals(homePage.getTitle(), "GitHub: Let’s build from here · GitHub");
    }

    @Test
    public void testSuccessfulLoginPageFactory(ITestContext testContext) {
        PageFactoryLoginPage pageFactoryLoginPage = new PageFactoryLoginPage(driver);
        pageFactoryLoginPage.goToLoginPage();
        pageFactoryLoginPage.setUsername(PropertyUtils.get("github.username", testContext));
        pageFactoryLoginPage.setPassword(PropertyUtils.get("github.password", testContext));
        pageFactoryLoginPage.clickCommit();
    }

}
