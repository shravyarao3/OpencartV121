package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener
{
	public ExtentSparkReporter sparkReporter; // UI of the report
	public ExtentReports extent; //Populate common info of the report
	public ExtentTest test; //Creating test case entries in the report and update status of the test methods
	String repName;
	
	public void onStart(ITestContext testContext)
	{/*
		SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt=new Date();
		String currentdatetimestamp=df.format(dt); */
		
		String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); //timestamp
	    repName="Test-Report-"+timeStamp+".html";
	    sparkReporter=new ExtentSparkReporter(".\\reports\\"+repName); //Specify location of the report
	    sparkReporter.config().setDocumentTitle("Opencart Automation Report"); //Title of report
	    sparkReporter.config().setReportName("Opencart Functional Report"); //Name of the report
	    sparkReporter.config().setTheme(Theme.STANDARD);
	    
	    extent=new ExtentReports();
	    extent.attachReporter(sparkReporter);
	    extent.setSystemInfo("Application", "Opencart");
	    extent.setSystemInfo("Module", "Admin");
	    extent.setSystemInfo("SubModule", "Customers");
	    extent.setSystemInfo("UserName", System.getProperty("user.name"));
	    extent.setSystemInfo("Environment", "QA");
	    
	    String os= testContext.getCurrentXmlTest().getParameter("os");
	    extent.setSystemInfo("Operating System", os);
	    
	    String browser= testContext.getCurrentXmlTest().getParameter("browser");
	    extent.setSystemInfo("Browser", browser);
	    
	  List <String> includedGroups= testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty())
		{
			extent.setSystemInfo("Groups",includedGroups.toString());
		}
	}
	
	public void onTestSuccess(ITestResult result)
	{
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); //to display groups in method
		test.log(Status.PASS, result.getName()+" got successfully executed");
	}
	
	public void onTestFailure(ITestResult result)
	{
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); //to display groups in method
		test.log(Status.FAIL, result.getName()+" got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());
		try 
		{
			String imgpath=new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgpath);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void onTestSkipped(ITestResult result)
	{
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); //to display groups in method
		test.log(Status.SKIP, result.getName()+"got Skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}
	public void onFinish(ITestContext testContext)
	{
	  extent.flush();
	  
	  String pathOfExtentReport=System.getProperty("user.dir")+"\\reports\\"+repName;
	  File extentReport =new File(pathOfExtentReport);
	  try 
	  {
		  Desktop.getDesktop().browse(extentReport.toURI());
	  }
	  catch(IOException e)
	  {
		  e.printStackTrace();
	  }
	  
	}
}
	
	
