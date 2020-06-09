package twitter.location.program.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class location_analysis_runner
{
    public static void main(String[] args) 
    {
	Result res = JUnitCore.runClasses(location_analysis_tests.class);
	
	for (Failure fail : res.getFailures()) 
	{
	    System.out.println("Test failed - " + fail.toString());
	}
	
	int runCount = res.getRunCount();
	
	System.out.println("Tests took " + res.getRunTime() / 1000.0 
		+ " seconds. Correct assertions: " + (runCount - res.getFailureCount()) + " ouf of " + runCount + "!");
    }
}
