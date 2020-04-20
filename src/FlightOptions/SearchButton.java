package FlightOptions;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import static StartUp.Initializer.dr;

public class SearchButton {
	@Test
	public void clickSearchButton(){
		Actions a = new Actions(dr);
		a
				.moveToElement(dr.findElement(By.xpath("//input[@name='ControlGroupSearchView$AvailabilitySearchInputSearchView$ButtonSubmit']")))
				.click()
				.build()
				.perform();
	}
}
