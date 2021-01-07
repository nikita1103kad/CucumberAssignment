package stepDefinitions;

import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Properties;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class AmazonStepDef {
	 WebDriver driver;
	public Properties prop;
	
	@Before
	public void abc()
	{
		prop=new Properties();
		try 
		{
			prop.load(new FileInputStream("E:\\new\\CucumberA\\src\\test\\java\\stepDefinitions\\PageObj.properties"));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Given("I open {string} browser")
	public void open_browser(String browser) throws Exception
	{
		if(browser.equalsIgnoreCase("chrome")) 
			{
		      System.setProperty("webdriver.chrome.driver",".\\Driver\\chromedriver.exe");
			  ChromeOptions options = new ChromeOptions();
			  options.addArguments("start-maximized");
			  options.addArguments("disable-infobars");
			  options.addArguments("--disable-extensions");
			  options.addArguments("--disable-notifications");
			  driver=new ChromeDriver(options);
			 }
		else if(browser.equalsIgnoreCase("firefox")) 
			 {
			  System.setProperty("webdriver.gecko.driver",".\\Driver\\geckodriver.exe");  
//			  FirefoxOptions options = new FirefoxOptions();
//			  options.addArguments("start-maximized");
//			  options.addArguments("disable-infobars");
//			  options.addArguments("--disable-extensions");
//			  options.addArguments("--disable-notifications");
			  driver=new FirefoxDriver();		    	
		     }

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	 }

	@When("I open (.*) page")
	public void open_Application() 
	{
		driver.get(prop.getProperty("URL"));
	}

	@When("I search {string} in search bar")
	public void search(String product)
	{
		driver.findElement(By.id(prop.getProperty("searchButton"))).click();
	    driver.findElement(By.id(prop.getProperty("searchButton"))).sendKeys(product);
		driver.findElement(By.id(prop.getProperty("submit"))).submit();
	}
	
	@When("I sort price from high to low")
	public void sort_Price()
	{
		Select s= new Select(driver.findElement(By.id(prop.getProperty("sortButtonVisible"))));
		s.selectByIndex(2);
		driver.findElement(By.id(prop.getProperty("sortHighToLow"))).click();
		driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS); 
	}
	
	@When("I click on second highest product")
	public void second_Product() 
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0,250)");
		WebDriverWait x=new WebDriverWait(driver, 50);
		x.until(
				ExpectedConditions.elementToBeClickable(By.cssSelector(prop.getProperty("nikonLink")))
				);
	    driver.findElement(By.cssSelector(prop.getProperty("nikonLink"))).click(); 
   }
	
	@Then("I verify title contains {string}")
	public void title(String expectedTitle) 
	{
		driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
	    String oldTab = driver.getWindowHandle();
		ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
		newTab.remove(oldTab); 
		driver.switchTo().window(newTab.get(1));
		
		String Title= driver.findElement(By.id(prop.getProperty("title"))).getText();
		assertTrue("Verified",Title.contains(expectedTitle));	   
   }
	
    @Then("I close browser")
	public void close_browser()
	{
	    driver.quit();
	}
}
