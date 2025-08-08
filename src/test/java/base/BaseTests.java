package base;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import utils.ConfigReader;
import utils.DriverManager;
import utils.LoggerLoad;



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
	    
	    
	     @AfterMethod
	    public  void after() throws Throwable {
	        driverFactory.quitDriver();
	    }

		
	}
