package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.Homepage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass
{
	@Test(groups={"Sanity","Master"})
     public void verify_login()
     {
		try 
		{ 
			logger.info("**Starting TC002_LoginTest*");
			Homepage hp=new Homepage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			
			LoginPage lg=new LoginPage(driver);
			lg.setEmail(p.getProperty("email"));
			lg.setPassword(p.getProperty("password"));
			lg.clickLogin();
			
			MyAccountPage mypage=new MyAccountPage(driver);
			boolean targetpage=mypage.isMyAccountPageExists();
			Assert.assertTrue(targetpage); //Assert.assertEquals(targetpage, true, "Login Failed");	
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		logger.info("**Finished TC002_LoginTest*");
     }
}
