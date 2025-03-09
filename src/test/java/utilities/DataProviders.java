package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders 
{
   //DataProvider1
	@DataProvider(name="LoginData")
	public String [][] getData() throws IOException 
	{
		String path=".\\testData\\Opencart_LoginData.xlsx"; //taking excel file from testdata
		ExcelUtility xlutility= new ExcelUtility(path); //Creating an object for utility
		int total_rowcount=xlutility.getRowCount("Sheet1");
		int total_colcount=xlutility.getCellCount("Sheet1", total_rowcount);
		String logindata[][]= new String[total_rowcount][total_colcount]; //Created for two dimension array which can store data
		for(int r=1;r<=total_rowcount;r++) //1 //Read the data from excel and storing into two dimensional array
		{
			for(int c=0;c<total_colcount; c++)//0 r is row, c is coulmn
			{
				logindata[r-1][c]=xlutility.getCellData("Sheet1", r, c); //1,0
			}
		}
		
	   return logindata; //returning two dimension array
	}
}
