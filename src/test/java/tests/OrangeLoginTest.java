package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.BaseTests;
import page.OrangeLoginPage;

public class OrangeLoginTest extends BaseTests {
    OrangeLoginPage login;

    @BeforeMethod
    @Parameters("browser")
    public void setupTest(String browser) {
        login = new OrangeLoginPage(driver);
    }

    
   

@Test(dataProvider = "OrangeLoginTestData", dataProviderClass = page.OrangeLoginPage.class)
public void testLogin(String username, String password, String expectedError, String errorType) {
   // LoginPage loginPage = new LoginPage(getDriver());
	login.orangeLogin(username, password);

    switch (errorType.toLowerCase()) {
        case "popup":
        	//pop up message Invalid credentials
            String popupMsg = login.getPopupErrorMessage();
            Assert.assertEquals(popupMsg, expectedError);
            break;

        case "empty-user":
        	//blank username case
            String userError = login.getInlineErrorMessage();
            Assert.assertEquals(userError, expectedError);
            break;

        case "empty-pass":
        	//blank password case
            String passError = login.getInlineErrorMessage();
            Assert.assertEquals(passError, expectedError);
            break;

        case "empty-both":
        	//blank username and password case
            Assert.assertEquals(login.getInlineErrorMessage(), expectedError);
            break;

        case "none":
        	//Gets the current URL of the browser after login Checks if the URL contains the word "dashboard" 
        	//(which indicates login success in OrangeHRM) if the test failes "Login faield message is displayed
            Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Login failed");
            break;

        default:
            Assert.fail("Invalid errorType in test data: " + errorType);
    }
}

}