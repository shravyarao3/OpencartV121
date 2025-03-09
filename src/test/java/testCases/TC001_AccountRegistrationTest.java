package testCases;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.Homepage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass
{
   @Test(groups={"Regression","Master"})
   public void verify_account_registration()
   {
	   logger.info("**Starting TC001_AccountRegistrationTest **");
	   try
	   {
	   Homepage hp=new Homepage(driver);
	 
       hp.clickMyAccount();
       logger.info("Clicked on MyAccount link");
       hp.clickRegister();
       logger.info("Clicked on Register link");
       
       AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
       logger.info("Providing customer details..");
       regpage.setFirstName(randomString().toUpperCase());
       regpage.setLastName(randomString().toUpperCase());
       regpage.setEmail(randomString()+"@gmail.com");
       regpage.setTelephone(randomNumber());
       String randompwd=randomAlphaNumeric();
       regpage.setPassword(randompwd);
       regpage.setConfirmPassword(randompwd);
       regpage.setPrivacypolicy();
       regpage.clickContinue();
       logger.info("Validating expected message..");
       String confmsg=regpage.getConfirmationmsg();
       if(confmsg.equals("Your Account Has Been Created!"))
          {
    	     Assert.assertTrue(true);
    	   }
       else
       {
    	   logger.error("Test Failed");
    	   logger.debug("Debug loggs");
    	   Assert.assertTrue(false);
       }
	   }
	   catch(Exception e)
	   {
		   Assert.fail();
	   }
	   logger.info("**Finished TC001_AccountRegistrationTest**");
   }     
}
