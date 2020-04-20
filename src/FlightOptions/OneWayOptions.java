package FlightOptions;

import StartUp.Initializer;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
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
		Actions a = new Actions(dr);
		a
				.moveToElement(dr.findElement(By.xpath(String.format("//a[@value='%s']", pro.getProperty("depCityCode")))))
				.click()
				.build()
				.perform();
	}
	@Test(enabled = false) //website opens the arrival box automatically, test is redundant
	public void selectArrivalCityBox(){
		Actions a = new Actions(dr);
		a
				.moveToElement(dr.findElement(By.xpath("//input[@id='ControlGroupSearchView_AvailabilitySearchInputSearchViewdestinationStation1_CTXT']")))
				.click()
				.build()
				.perform();
	}
	@Test(dependsOnMethods = "selectDepartureCity")
	public void selectArrivalCity(){
		Actions a = new Actions(dr);
		a
				.moveToElement(dr.findElement(By.xpath(String.format("//div[@id='glsControlGroupSearchView_AvailabilitySearchInputSearchViewdestinationStation1_CTNR']//table[1]//tbody[1]//tr[2]//td[2]//div[3]//div[1]//div[1]//ul//li//a[@value='%s']", pro.getProperty("arrCityCode")))))
				.click()
				.build()
				.perform();
	}
}
