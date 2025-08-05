package utils;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

	public class TestListener implements ITestListener {
		
		WebDriver driver =DriverManager.getDriver(); 
		ExtentReports extentReport;
		ExtentTest extentTest;
		ExtentSparkReporter sparkReport;
	  
		@Attachment(value = "Screenshot of {0}", type = "image/png")
	    public static byte[] getScreenShot(WebDriver driver) {
			//driver = DriverManager.getDriver();
	        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	    }
		
		
	  public void onStart(ITestContext context) {
			  	sparkReport = new ExtentSparkReporter("./target/extent-reports/extent-report.html");	
				extentReport = new ExtentReports();
				extentReport.attachReporter(sparkReport);
				extentReport.getReport();
		    }
		

	    @Override
	    public void onTestStart(ITestResult result) {
	    	extentTest = extentReport.createTest(result.getName());
	    }

	    @Override
	    public void onTestSuccess(ITestResult result) {
	    	extentTest.log(Status.PASS, result.getName() + " Passed");
	    	
	    }

	    @Override
	    public void onTestFailure(ITestResult result) {
	    	 String screenshotName = result.getMethod().getMethodName();
	        extentTest.log(Status.FAIL, result.getName() + " Failed");
	        driver =DriverManager.getDriver(); 
	        String screenshotPath = captureScreenshot(result.getName()); 
	        extentTest.addScreenCaptureFromPath(screenshotPath);  // Add screenshot to the report
	        
	        
	        //allure report
	     	Allure.addAttachment(screenshotName, new ByteArrayInputStream(getScreenShot(driver)));
	    }

	    @Override
	    public void onTestSkipped(ITestResult result) {
	    	
	    	 extentTest.log(Status.SKIP, result.getName() + " Skipped");
	    }



	    @Override
	    public void onFinish(ITestContext context) {
	    	 extentReport.flush();
	       
	    }

	    // Method to capture the screenshot
	    private String captureScreenshot(String testName) {
	        TakesScreenshot ts = (TakesScreenshot) driver;
	        File source = ts.getScreenshotAs(OutputType.FILE);
	        String dest = System.getProperty("user.dir") + "/screenshots/" + testName + ".png";
	        File destination = new File(dest);
	        try {
	            FileUtils.copyFile(source, destination);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return dest;
	    }
}
