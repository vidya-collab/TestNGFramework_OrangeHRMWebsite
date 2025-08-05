package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

	  public int retryCount = 0;
	    private static final int maxRetryCount = ConfigReader.getMaxRetryCount(); 

	    @Override
	    public boolean retry(ITestResult result) {
	        if (retryCount < maxRetryCount) {
	            retryCount++;
	            System.out.println("Retrying test " + result.getName() + " with status "
	                + getResultStatusName(result.getStatus()) + " for the " + retryCount + " time.");
	            return true;
	        }
	        return false;
	    }

	    public String getResultStatusName(int status) {
	        String resultName = null;
	        if (status == 1)
	            resultName = "SUCCESS";
	        if (status == 2)
	            resultName = "FAILURE";
	        if (status == 3)
	            resultName = "SKIP";
	        return resultName;
	    }
	}


