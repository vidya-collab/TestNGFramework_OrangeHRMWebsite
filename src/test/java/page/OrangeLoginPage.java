package page;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
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

    @FindBy(xpath = "//input[@name='username']")
    private WebElement usernameField;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;

    @FindBy(xpath = "//span[text()='Dashboard']")
    private WebElement dashBoard;
    
    @FindBy(xpath = "//p[contains(@class,'oxd-text oxd-text--p oxd-alert-content-text')]")
    private WebElement errorMsg;
    
   

    public OrangeLoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    
    public void orangeLogin(String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='username']")));
        usernameField.sendKeys(username);

        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys(password);

        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }


//    public String processCredentials() {
//        String username = usernameField.getDomProperty("value");
//        System.out.println(username);
//        //getAttribute("value").trim();
//        
//        String password = passwordField.getDomProperty("value");
//        //getAttribute("value").trim();
//
//        if (username.isEmpty() && password.isEmpty()) {
//            return usernameField.getDomProperty("validation message");
//            		//getAttribute("validationMessage");
//        } else if (username.isEmpty()) {
//            return usernameField.getDomProperty("validation message");
//            		//getAttribute("validationMessage");
//        } else if (password.isEmpty()) {
//            return passwordField.getDomProperty("validation message");
//            		//getAttribute("validationMessage");
//        } else {
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//
//            try {
//                // Wait for dashboard element or error message to appear
//                wait.until(ExpectedConditions.or(
//                    ExpectedConditions.visibilityOf(dashBoard),
//                    ExpectedConditions.visibilityOf(errorMsg)
//                ));
//
//                if (dashBoard.isDisplayed()) {
//                    return dashBoard.getText();
//                } else {
//                    return errorMsg.getText();
//                }
//            } catch (Exception e) {
//                return "Neither dashboard nor error message appeared.";
//            }
//        }
//    }
    
//    public String processCredentials() {
//        String username = usernameField.getDomProperty("value").trim();
//        String password = passwordField.getDomProperty("value").trim();
//
//        if (username.isEmpty() && password.isEmpty()) {
//            return usernameField.getAttribute("validationMessage");
//        } else if (username.isEmpty()) {
//            return usernameField.getAttribute("validationMessage");
//        } else if (password.isEmpty()) {
//            return passwordField.getAttribute("validationMessage");
//        } else {
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//
//            try {
//                // Wait for either dashboard or error message
//                wait.until(ExpectedConditions.or(
//                    ExpectedConditions.visibilityOf(dashBoard),
//                    ExpectedConditions.visibilityOf(errorMsg)
//                ));
//
//                if (dashBoard.isDisplayed()) {
//                    return dashBoard.getText();
//                } else {
//                    return errorMsg.getText();
//                }
//            } catch (Exception e) {
//                return "Neither dashboard nor error message appeared.";
//            }
//        }
    
    //------ getAttribute() is depricated ,then tried with getDomProperty() 
    //------then understanding is "required" message is not browser specific ,it is only for that( username or password )field so used getDomProperty () will not return anything. 
    //-------then used getText()
    
//    public String processCredentials() {
//        String username = usernameField.getDomProperty("value").trim();
//        String password = passwordField.getDomProperty("value").trim();
//
//        loginButton.click(); // Always click login to trigger message
//
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//
//        if (username.isEmpty() && password.isEmpty()) {
//            WebElement userError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='username']/ancestor::div[contains(@class,'oxd-input-group')]//span")));
//            return userError.getText(); // returns "Required"
//        } else if (username.isEmpty()) {
//            WebElement userError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='username']/ancestor::div[contains(@class,'oxd-input-group')]//span")));
//            return userError.getText(); // returns "Required"
//        } else if (password.isEmpty()) {
//            WebElement passError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password']/ancestor::div[contains(@class,'oxd-input-group')]//span")));
//            return passError.getText(); // returns "Required"
//        } else {
//            try {
//                wait.until(
//                    ExpectedConditions.visibilityOf(dashBoard));
//                    // ExpectedConditions.visibilityOf(errorMsg)
//               // ));
//
//                if (dashBoard.isDisplayed()) {
//                    return dashBoard.getText();
//                } 
//                //else {
//                    //return errorMsg.getText();
//                //}
//            } catch (Exception e) {
//                return "Neither dashboard nor error message appeared.";
//            }
//        }
//    }

    public String processCredentials() {
        String username = usernameField.getDomProperty("value").trim();
        String password = passwordField.getDomProperty("value").trim();

        //loginButton.click(); // Always click login to trigger validation

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Common locators
        By usernameErrorLocator = By.xpath("//input[@name='username']/ancestor::div[contains(@class,'oxd-input-group')]//span");
        By passwordErrorLocator = By.xpath("//input[@type='password']/ancestor::div[contains(@class,'oxd-input-group')]//span");

        try {
            if (username.isEmpty() && password.isEmpty()) {
                WebElement userError = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameErrorLocator));
                return userError.getText(); // Typically "Required"
            } else if (username.isEmpty()) {
                WebElement userError = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameErrorLocator));
                return userError.getText();
            } else if (password.isEmpty()) {
                WebElement passError = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordErrorLocator));
                return passError.getText();
            } else {
                // Wait for either dashboard or error message
                wait.until(
                    ExpectedConditions.visibilityOf(dashBoard));
                   // ExpectedConditions.visibilityOf(errorMsg)
                //));

                if (dashBoard.isDisplayed()) {
                    return dashBoard.getText(); // e.g., "Dashboard"
                } //else if (errorMsg.isDisplayed()) {
                   // return errorMsg.getText(); // e.g., "Invalid credentials"
                //} 
                else  {
                    return "Unknown state: neither dashboard nor error visible.";
                }
            }
        } catch (Exception e) {
            return "Exception occurred: " + e.getMessage();
        }
    }


    
    
    @DataProvider(name = "OrangeLoginTestData")
    public static Object[][] getOrangeLoginTestData() {
        String excelPath = ConfigReader.excelpath();  // returns Excel path
        Object[][] testData = ExcelReader.readExcelData(excelPath, "OrangeLogin");  // adjust sheet name
        return testData;
    }    
    
}
