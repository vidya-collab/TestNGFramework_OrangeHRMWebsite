package page;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;

import utils.ConfigReader;
import utils.DriverManager;
import utils.ExcelReader;


public class OrangeLoginPage {
	WebDriver driver = DriverManager.getDriver();
	ConfigReader configFileReader = DriverManager.configReader();
	String excelPath = ConfigReader.excelpath();
	ExcelReader excelReader = new ExcelReader(excelPath);
	WebDriverWait wait;

 

    public OrangeLoginPage(WebDriver driver) {
    	this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    
    private By username = By.name("username");
    private By password = By.name("password");
    private By loginBtn = By.tagName("button");
    // Popup-style error (e.g., "Invalid credentials")
    private By popupError = By.xpath("//p[contains(@class,'oxd-alert')]");
    // Inline error for required fields
    private By inlineErrors = By.xpath("//span[contains(@class,'oxd-input-field-error-message')]");

    public void orangeLogin(String user, String pass) {
    	WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(username));
    	usernameField.clear();
    	usernameField.sendKeys(user);
    	WebElement passwordField =  wait.until(ExpectedConditions.visibilityOfElementLocated(password));
    	passwordField.clear();
    	passwordField.sendKeys(pass);
    	WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(loginBtn));
    	loginButton.click();
    }

    // For invalid credentials popup
    public String getPopupErrorMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(popupError));
        return driver.findElement(popupError).getText();
    }
    // For "Required" inline field messages 
    public String getInlineErrorMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inlineErrors));
        return driver.findElement(inlineErrors).getText();
    }  
    
    @DataProvider(name = "OrangeLoginTestData")
    public static Object[][] getOrangeLoginTestData() {
        String excelPath = ConfigReader.excelpath();  // returns Excel path
        Object[][] testData = ExcelReader.readExcelData(excelPath, "OrangeLogin");  // adjust sheet name
        return testData;
    }

}

