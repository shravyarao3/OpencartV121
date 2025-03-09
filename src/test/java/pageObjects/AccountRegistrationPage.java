package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage
{
   public AccountRegistrationPage(WebDriver driver)
	{
		super(driver);
	}
   
   @FindBy(xpath="//input[@id='input-firstname']") WebElement txt_Firstname;
   @FindBy(xpath="//input[@id='input-lastname']") WebElement txt_Lastname;
   @FindBy(xpath="//input[@id='input-email']") WebElement txt_Email;
   @FindBy(xpath="//input[@id='input-telephone']") WebElement txt_Telephone;
   @FindBy(xpath="//input[@id='input-password']") WebElement txt_Password;
   @FindBy(xpath="//input[@id='input-confirm']") WebElement txt_ConfirmPassword;
   @FindBy(xpath="//input[@name='agree']") WebElement chkdPolicy;
   @FindBy(xpath="//input[@value='Continue']") WebElement btnContinue;
   @FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']") WebElement msgConfirmation;
   
    public void setFirstName(String fname)
    {
    	txt_Firstname.sendKeys(fname);
    }
    
    public void setLastName(String lname)
    {
    	txt_Lastname.sendKeys(lname);
    }
    public void setEmail(String email)
    {
    	txt_Email.sendKeys(email);
    }
    public void setTelephone(String tel)
    {
    	txt_Telephone.sendKeys(tel);
    }
    public void setPassword(String pwd)
    {
    	txt_Password.sendKeys(pwd);
    }
    public void setConfirmPassword(String pwd)
    {
    	txt_ConfirmPassword.sendKeys(pwd);
    }
    
   public void setPrivacypolicy()
   {
	   chkdPolicy.click();
   }
   public void clickContinue()
   {
	  JavascriptExecutor js=(JavascriptExecutor)driver;
	  js.executeScript("arguments[0].click();", btnContinue);
   }
   
   public String getConfirmationmsg()
   {
	   try 
	   {
		   return  msgConfirmation.getText();
	   }
	   catch(Exception e)
	   {
		   return e.getMessage();
	   }
   }
}








