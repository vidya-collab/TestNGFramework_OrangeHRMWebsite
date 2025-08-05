package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.BaseTests;
import page.OrangeLoginPage;
import utils.LoggerLoad;

public class OrangeLoginTest extends BaseTests {
    OrangeLoginPage login;

    @BeforeMethod
    @Parameters("browser")
    public void setupTest(String browser) {
        //super.setup(browser);
        login = new OrangeLoginPage(driver);
    }

   //@Test(dataProvider = "OrangeLoginTestData", dataProviderClass = page.OrangeLoginPage.class, retryAnalyzer = utils.RetryAnalyzer.class)
    @Test(dataProvider = "OrangeLoginTestData", dataProviderClass = page.OrangeLoginPage.class)
    public void testLoginPage(String Username, String Password, String ExpectedMessage) {
        login.orangeLogin(Username, Password);

        String actualResult = login.processCredentials();

        LoggerLoad.info("Actual result: " + actualResult);
        LoggerLoad.info("Expected result: " + ExpectedMessage);

        Assert.assertEquals(actualResult.trim(), ExpectedMessage.trim(), "Login test failed!");
    }
}

