package QualityKiosksTraining.AutomationFrameworkAPI;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import QualityKiosksTraining.AutomationFrameworkAPI.Utilities.JavaUtilities;

public class WebTest 
{

	RemoteWebDriver Driver;
	public HashMap<String,WebElement>ObjectRepo=new HashMap<String,WebElement>();
	public void StartTest(String BrowserName)
	{
		
		OpenBrowser(BrowserName);
		OpenURL();
	}

	public void CreateObjectRepository(String ObjectRepositoryFileName)
	{
		
		//String ObjectRepositoryFilePath="D:\\QualityKioskTraining\\TestCasesProject\\OrangeHRMTestCases\\ObjectRepositories\\"+ObjectRepositoryFileName+".txt";
		String ObjectRepositoryFilePath=JavaUtilities.EnvVars.get("ObjectRepositoriesLocation")+"\\"+ObjectRepositoryFileName+".txt";
		
		try 
		{
			
			FileReader FR=new FileReader(ObjectRepositoryFilePath);
			BufferedReader BR = new BufferedReader(FR);
			String Line=BR.readLine();
			while(Line!=null)
			{
				String []Pieces=Line.split(",");
				String LogicalName=Pieces[0];
				String IdentificationStratergyToken=Pieces[1];
				String IdentificationLocator=Pieces[2];
				WebElement E=FindAndReturnElement(IdentificationStratergyToken,IdentificationLocator);
				ObjectRepo.put(LogicalName, E);
				Line=BR.readLine();
			}	
			
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("Please check if file exists at location : "+ObjectRepositoryFileName);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Problem in reading file exists at location : "+ObjectRepositoryFileName);
		}
		
		
	}
	
	public WebElement FindAndReturnElement(String ElementIdentificationStratergy,String Locator)
	{
		WebElement E=null;
		switch(ElementIdentificationStratergy)
		{
		
			case "BY_NAME":
				E=Driver.findElementByName(Locator);
				break;
			case "BY_ID":
				E=Driver.findElementById(Locator);
				break;
			case "BY_CSS":
				E=Driver.findElementByCssSelector(Locator);
				break;
			case "BY_XPATH":
				E=Driver.findElementByXPath(Locator);
				break;
			case "BY_TAGNAME":
				E=Driver.findElementByTagName(Locator);
				break;
		}
		return E;
	}
	public void OpenBrowser(String Browser)
	{
		switch(Browser)
		{
			case "CHROME":
				////System.setProperty("webdriver.chrome.driver", "D:\\QualityKioskTraining\\Drivers\\chromedriver.exe");
				///System.setProperty("webdriver.chrome.driver", JavaUtilities.EnvVars.get(0));
				System.setProperty("webdriver.chrome.driver", JavaUtilities.EnvVars.get("ChromeDriverPath"));
				Driver=new ChromeDriver();
				break;
			case "FIREFOX":
				//System.setProperty("webdriver.gecko.driver", "D:\\QualityKioskTraining\\Drivers\\geckodriver.exe");
				//System.setProperty("webdriver.chrome.driver", JavaUtilities.EnvVars.get(2));
				System.setProperty("webdriver.chrome.driver", JavaUtilities.EnvVars.get("FirefoxDriverPath"));
				Driver=new FirefoxDriver();
				break;
				
			case "IE":
				
		}
		
	}
	
	public void OpenURL()
	{
		
		//Driver.get("https://opensource-demo.orangehrmlive.com");
		////Driver.get(JavaUtilities.EnvVars.get(1));
		Driver.get(JavaUtilities.EnvVars.get("ApplicationURL"));
	}
	
	public void EnterText(WebElement E,String ValueToType)
	{
		E.clear();
		E.sendKeys(ValueToType);
	}
	public void ClearText(WebElement E)
	{
		E.clear();
	}
	public void AppendText()
	{
		
	}
	public void ClickElement(WebElement E)
	{
		E.click();
	}

	public String GetElementText(WebElement E)
	{
		return E.getText();
	}
	
	public void GetURL()
	{
		Driver.getCurrentUrl();
	}
}
