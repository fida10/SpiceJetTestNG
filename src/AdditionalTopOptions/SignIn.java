package AdditionalTopOptions;

import StartUp.Initializer;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SignIn extends Initializer {
	@BeforeTest
	public static void openLoginPage(){
		dr.get("https://book.spicejet.com/Login.aspx");
	}
	@Parameters({"userName","passWord"})
	@Test
	public static void loginRealUserName(String userName, String passWord) throws InterruptedException{
		Actions a = new Actions(dr);
		a
				.moveToElement(dr.findElement(By.xpath("//input[@type = 'text' and @placeholder='Mobile Number']")))
				.click()
				.sendKeys("123")
				.build()
				.perform();
		Thread.sleep(200); //executes far too fast, forces the page downwards and doesn't allow the page to catch up. This could be due to the dynamic nature of the dropdown, so a forced wait must be instated.
		a
				.moveToElement(dr.findElement(By.xpath("//div[@aria-owns = 'country-listbox']")))
				.click()
				.sendKeys("United States")
				.moveToElement(dr.findElement(By.xpath("//li[@data-country-code = 'us']")))
				.click()
				.build()
				.perform();
		a
				.moveToElement(dr.findElement(By.xpath("//input[@type = 'text' and @placeholder='Mobile Number']")))
				.click()
				.sendKeys(userName)
				.build()
				.perform();
		a
				.moveToElement(dr.findElement(By.xpath("//input[@type = 'password' and @class='wLrg3t required']")))
				.click()
				.sendKeys(passWord)
				.build()
				.perform();
		a
				.moveToElement(dr.findElement(By.xpath("//input[@type = 'submit' and @class='buttonLogin']")))
				.click()
				.build()
				.perform();
	}
	@Test(dependsOnMethods = "loginRealUserName")
	public void signOut(){
		dr.findElement(By.xpath("//ul[@id='top-header']//li//a[@id='Login']")).click();
	}
}
