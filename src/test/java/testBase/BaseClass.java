package testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
public class BaseClass 
{
	public static WebDriver driver;
	public Logger logger; //Log4j
	public Properties p;
	
	   @BeforeClass(groups= {"Sanity","Regression","Master"})
	   @Parameters({"os","browser"})
	   public void setup(String os, String br) throws IOException
	   {
		   //loading config.prperties file
		   FileInputStream file=new FileInputStream("./src/test/resources/config.properties");
		   p=new Properties();
		   p.load(file);
		   
		   logger=LogManager.getLogger(this.getClass()); //loading log4j2.xml
		   
		   if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		   {
			   DesiredCapabilities capabilities=new DesiredCapabilities();
			   //os
			   if(os.equalsIgnoreCase("Windows"))
			   {
				   capabilities.setPlatform(Platform.WIN11);
			   }
			   else if (os.equalsIgnoreCase("mac"))
			   {
				   capabilities.setPlatform(Platform.MAC);   
			   }
			   else
			   {
				   System.out.println("No Matching os");
				   return;
			   }
			   
			   //Browser
			   switch(br.toLowerCase())
			   {
			   case "chrome": capabilities.setBrowserName("chrome"); break;
			   case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
			   default: System.out.println("No Matching browser"); return;
			   }
			   driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
		   }
		   if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		   {
		   switch(br.toLowerCase())
		   {
		   case "chrome":driver=new ChromeDriver(); break;
		   case "edge":driver=new EdgeDriver(); break;
		   default :System.out.println("Invalid Browser"); return;
		   }
		   }
		   driver.manage().deleteAllCookies();
		   driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		   driver.get(p.getProperty("appurl")); //Reading URL from properties file
		   driver.manage().window().maximize(); 
	   }

	   @AfterClass(groups={"Sanity","Regression","Master"})
	   public void teardown()
	   {
		   driver.quit();
	   }
	   
	   public String randomString()
	   {
		  String generatedString= RandomStringUtils.randomAlphabetic(5);
		  return generatedString;
	   }
	   
	   public String randomNumber()
	   {
		  String generatedNumber= RandomStringUtils.randomNumeric(10);
		  return generatedNumber;
	   }
	   
	   public String randomAlphaNumeric()
	   {
		  String generatedString= RandomStringUtils.randomAlphabetic(3);
		  String generatedNumber= RandomStringUtils.randomNumeric(3);
		  return (generatedString+"@"+generatedNumber);
	   }
	   
	   public String captureScreen(String tname) throws IOException
	   {
			String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			TakesScreenshot takesScreenshot=(TakesScreenshot) driver;
			File sourcefile=takesScreenshot.getScreenshotAs(OutputType.FILE);
			String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\"+tname+"_"+timeStamp+".png";
			File targetFile = new File (targetFilePath);
			sourcefile.renameTo(targetFile);
			return targetFilePath;
	   }
}
