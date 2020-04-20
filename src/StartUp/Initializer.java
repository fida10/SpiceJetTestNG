package StartUp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Initializer {

	public static WebDriver dr;
	public static Properties pro;

	@Parameters({"url"})
	@BeforeSuite
	public void webDriverInitializer(String url) throws IOException {
		pro = new Properties();
		FileInputStream proFile = new FileInputStream("/Users/fida10/Documents/Lynda Hackalicious exercise files/JavaPractice/SpiceJetTestNG/src/globalAccesProps.properties");
		pro.load(proFile);

		System.setProperty("webdriver.chrome.driver", "//Users//fida10//selenium//drivers//chrome//chromedriver");
		dr = new ChromeDriver();
		dr.manage().deleteAllCookies();
		dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		dr.get(url);
	}
	@AfterClass
	public static void screenShotAfterEveryClass(){
		//needs to be defined
	}
	@AfterSuite
	public void quitter(){
		System.out.println("All cases run, quitting.");
	//	dr.quit();
	}
}
