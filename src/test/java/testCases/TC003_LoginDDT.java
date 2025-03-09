package testCases;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjects.Homepage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

/* 
 Data is valid - login success - test pass - logout
 Data is valid - login fail - test failed
 
 Data is invalid - login fail- test pass
 Data is invalid - login success - test fail- logout 
 */
public class TC003_LoginDDT extends BaseClass
{
	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class, groups= {"Datadriven"})// getting dataprovider from different class
	public void verify_loginDDT(String email, String pwd, String exp_res)
	{
		try {
			logger.info("**Starting TC003_LoginDDT*");
			Homepage hp=new Homepage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			
			LoginPage lg=new LoginPage(driver);
			lg.setEmail(email);
			lg.setPassword(pwd);
			lg.clickLogin();
			
			MyAccountPage mypage=new MyAccountPage(driver);
			boolean targetpage=mypage.isMyAccountPageExists();
			
			if(exp_res.equalsIgnoreCase("Valid"))
			{
				if(targetpage==true)
				{
					mypage.clickLogout();
					Assert.assertTrue(true);
				}
				else
				{
					Assert.assertTrue(false);
				}
			}
			if(exp_res.equalsIgnoreCase("Invalid"))
			{
				if(targetpage==true)
				{
					mypage.clickLogout();
					Assert.assertTrue(false);
				}
				else
				{
					Assert.assertTrue(true);
				}
			}
		}
		catch(Exception e)
		{
			Assert.fail();
		}
			logger.info("**Finished TC003_LoginDDT*");
		
}
}
