package StartUp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static StartUp.ScreenShotMethods.deleteOldScreenShots;

public class Initializer {

	public static WebDriver dr;
	public static Properties pro;
	@BeforeSuite
	public void webDriverInitializer() throws IOException {
		pro = new Properties();
		FileInputStream proFile = new FileInputStream("/Users/fida10/Documents/Lynda Hackalicious exercise files/JavaPractice/SpiceJetTestNG/src/globalAccesProps.properties");
		pro.load(proFile);

		System.setProperty("webdriver.chrome.driver", "//Users//fida10//selenium//drivers//chrome//chromedriver");
		dr = new ChromeDriver();
		dr.manage().deleteAllCookies();
		dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		dr.get(pro.getProperty("url"));
	}
	@Test(enabled = false)
	public void deleteOldScreenshots() throws IOException{
		deleteOldScreenShots();
	}
	@AfterSuite
	public void quitter(){
		System.out.println("All cases run, quitting.");
		dr.quit();
	}
}
