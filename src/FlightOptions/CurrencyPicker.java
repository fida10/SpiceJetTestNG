package FlightOptions;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import static StartUp.Initializer.*;

public class CurrencyPicker {
	@Test
	public void currencyPicker(){
		Select currency = new Select(dr.findElement(By.xpath("//select[@id='ControlGroupSearchView_AvailabilitySearchInputSearchView_DropDownListCurrency']")));
		String currencySelection = pro.getProperty("currencyToUse");
		currency.selectByVisibleText(currencySelection);
	}
}
