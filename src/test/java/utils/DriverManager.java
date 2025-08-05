package utils;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;



public class DriverManager {
	public static  ConfigReader configFileReader = new ConfigReader();
    private static ThreadLocal<WebDriver> tldriver = new ThreadLocal<>();

    public static WebDriver initializeDriver(String browser) {
        if (browser.equalsIgnoreCase("firefox")) {
            tldriver.set(new FirefoxDriver());
        } else if (browser.equalsIgnoreCase("chrome")) {
            tldriver.set(new ChromeDriver());
        } else if (browser.equalsIgnoreCase("safari")) {
            tldriver.set(new SafariDriver());
        } else if (browser.equalsIgnoreCase("edge")) {
            tldriver.set(new EdgeDriver());
        }
        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        return getDriver();
    }

    public static WebDriver getDriver() {
        return tldriver.get();
    }

    public void quitDriver() {
        if (tldriver.get() != null) {
            tldriver.get().quit();
            tldriver.remove();
        }
    }
    public static ConfigReader configReader() {
    	return configFileReader;
	}
}
