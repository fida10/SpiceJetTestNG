package FlightOptions;

import StartUp.Initializer;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class OneWayOptions extends Initializer {
	@BeforeClass
	public void selectOneWayButton(){
		dr.findElement(By.xpath("//div[@id='travelOptions']//div[1]//input[1]")).click();
	}
	@Test
	public void selectDepartureCityBox(){
		Actions a = new Actions(dr);
		a
				.moveToElement(dr.findElement(By.xpath("//input[@name='ControlGroupSearchView_AvailabilitySearchInputSearchVieworiginStation1_CTXT']")))
				.click()
				.build()
				.perform();
	}
	@Test(dependsOnMethods = "selectDepartureCityBox")
	public void selectDepartureCity() {
		String depCityCode = pro.getProperty("depCityCode");
		WebElement cityToSelectDep = dr.findElement(By.xpath("//html")); //just to initialize the cityToSelectDep variable. Every webpage as a //html tag, that's the base.
		try {
			cityToSelectDep = dr.findElement(By.xpath(String.format("//a[@value='%s']", depCityCode))); //ensures that the desired city specified above in properties is actually displayed. If not, this will throw an exception of NoSuchElementException.
		} catch (NoSuchElementException e) {
			System.out.println("Element not found, invalid city selection");
			selectArrivalCityBox();
			Assert.assertTrue(cityToSelectDep.isDisplayed()); //Since it threw the exception, the desired city pair must then not be visible, which means this statement will cause a test failure and move on to the next test. It will also open up the arrival box with "selectArrivalCityBox()", as this will now not be done automatically due to the failure of this test.
		}
		Actions a = new Actions(dr);
		a
				.moveToElement(cityToSelectDep)
				.click()
				.build()
				.perform();
		Assert.assertTrue(dr.findElement(By.xpath(String.format("//input[@selectedvalue='%s']", depCityCode))).isDisplayed()); //ensures the clicked value above is the one selected in the "from" input box
	}
	@Test(enabled = false) //website opens the arrival box automatically, test is redundant most of the time. However, if the departure city fails to select, arrival city box is not switched to automatically. This method seeks to remedy this, and it is called in selectDepartureCityBox when that test fails.
	public void selectArrivalCityBox(){
		Actions a = new Actions(dr);
		a
				.moveToElement(dr.findElement(By.xpath("//input[@id='ControlGroupSearchView_AvailabilitySearchInputSearchViewdestinationStation1_CTXT']")))
				.click()
				.build()
				.perform();
	}
	@Test(dependsOnMethods = "selectDepartureCity") //if selectDepartureCity is failed, this test is skipped automatically. Very convenient.
	public void selectArrivalCity(){
		String arrCityCode = pro.getProperty("arrCityCode");
		WebElement cityToSelectArr = dr.findElement(By.xpath((String.format("//div[@id='glsControlGroupSearchView_AvailabilitySearchInputSearchViewdestinationStation1_CTNR']//table[1]//tbody[1]//tr[2]//td[2]//div[3]//div[1]//div[1]//ul//li//a[@value='%s']", arrCityCode))));

		Assert.assertTrue(cityToSelectArr.isDisplayed()); //ensures that the desired city specified above in properties is actually displayed. If not, test will fail.
		Actions a = new Actions(dr);
		a
				.moveToElement(cityToSelectArr)
				.click()
				.build()
				.perform(); //a failsafe needs to be programmed so that if the airport code is invalid, the code does not just sit there waiting.
		Assert.assertTrue(dr.findElement(By.xpath(String.format("//input[@selectedvalue='%s']", arrCityCode))).isDisplayed()); //ensures the clicked value above is the one selected in the "to" input box
	}
	@AfterClass
	public void exitOpenDatePicker(){ //sometimes it stays stuck on one box

	}
}
