package base;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import page.OrangeLoginPage;
import utils.LoggerLoad;
import utils.ConfigReader;
import utils.ExcelReader;
import utils.DriverManager;
//import com.aventstack.chaintest.plugins.ChainTestListener;



 // @Listeners(ChainTestListener.class)
	public class BaseTests {
	    protected static WebDriver driver;
	    private static DriverManager driverFactory;
	    ConfigReader configFileReader = new ConfigReader();
	    
	    @Parameters("browser")
	    
	    @BeforeMethod
	    public void before() throws Throwable {
	        String browser = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browser");
	        if (browser == null) {
	            System.out.println("About to open browser: chrome");
	            driverFactory = new DriverManager();
	            driver.get(ConfigReader.getApplicationUrl());
	            driver = DriverManager.initializeDriver("chrome");
	        } else {
	            driverFactory = new DriverManager();
	            driver = DriverManager.initializeDriver(browser);
	            driver.get(ConfigReader.getApplicationUrl());
	            LoggerLoad.info("Initializing driver for : "+ browser);
	        }
	        
	    }
	    
	    
	    
	    public void validlogin() throws IOException, InvalidFormatException {
	    	OrangeLoginPage login;
	    	String excelPath = ConfigReader.excelpath();
	    	ExcelReader excelReader1 = new ExcelReader(excelPath);
			Object[][] validLoginData = excelReader1.readSheetWithColumns("Login", Arrays.asList("Username", "Password"));
			String Username = validLoginData[0][0].toString(); // Assuming the first row
			String Password = validLoginData[0][1].toString();
			login = new OrangeLoginPage(driver);
			login.orangeLogin(Username, Password);
	    }
	    
	     @AfterMethod
	    public  void after() throws Throwable {
	        driverFactory.quitDriver();
	    }

		
	}
