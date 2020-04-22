package FlightOptions;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static StartUp.Initializer.dr;
import static StartUp.Initializer.pro;

public class PaxPicker { //these need to be fixed, not working
	/*public static void main(String[] args){
		System.setProperty("webdriver.chrome.driver", "//Users//fida10//selenium//drivers//chrome//chromedriver");
		dr = new ChromeDriver();
		dr.manage().deleteAllCookies();
		dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		dr.manage().window().maximize();
		dr.get("https://book.spicejet.com/Search.aspx#");

		Actions a = new Actions(dr);
		a
				.moveToElement(dr.findElement(By.xpath("//div[@id='divpaxinfo']"))) //the xpath to the passengers box
				.click()
				.build()
				.perform();
	} */
	private static void openPaxBox(){
		Actions a = new Actions(dr);
		a
				.moveToElement(dr.findElement(By.xpath("//div[@id='divpaxinfo']"))) //the xpath to the passengers box
				.click()
				.build()
				.perform();
	}
	private static List<Integer> StringToIntForSelectBoxes(){ //converts provided string values in properties file to integers.
		String[] noOfEachPaxType = {pro.getProperty("noOfAdults"), pro.getProperty("noOfChildren"), pro.getProperty("noOfInfants")};
		List<String> noOfEachPaxTypeList = Arrays.asList(noOfEachPaxType);
		List<Integer> noOfEachPaxTypeListAsInt = new ArrayList<>();

		for(int i = 0; i < noOfEachPaxTypeList.size(); i++){
			Integer paxCount = Integer.parseInt(noOfEachPaxTypeList.get(i));
			noOfEachPaxTypeListAsInt.add(paxCount);
		}
		return noOfEachPaxTypeListAsInt;
	}
	private static List<Integer> tooManyPickedStopper(){
		Assert.assertTrue(StringToIntForSelectBoxes().get(0) < 10); //no of adults selected cannot be greater than 9, else test will fail
		Assert.assertTrue(StringToIntForSelectBoxes().get(1) < 5); //no of children selected cannot be greater than 4, else test will fail
		Assert.assertTrue(StringToIntForSelectBoxes().get(2) < 5); //no of infants selected cannot be greater than 4, else test will fail
		return StringToIntForSelectBoxes();
	}
	private static void handleOverPax(){
		System.out.println("Greater than 9 pax is selected (excluding infants), OR, Greater number of infants than adults. Launching the popup handler and failing the test");
		dr.switchTo().alert().accept();
		System.out.println("Please reduce number of pax. Test will now fail because total pax count excluding infants is over 9, or have more infants than adults");//Assert statement for failure will be in main test because values change
	}
	@Test
	public void adultChildInfantNoPicker() throws InterruptedException{
		Thread.sleep(200);
		openPaxBox();
		Thread.sleep(200);
		//Adult
		Select adult = new Select(dr.findElement(By.xpath("//select[@id='ControlGroupSearchView_AvailabilitySearchInputSearchView_DropDownListPassengerType_ADT']")));
		String adultsToSelect = tooManyPickedStopper().get(0).toString(); //debugging statement to see value
		System.out.println(adultsToSelect);  //debugging statement to see value
		adult.selectByVisibleText(adultsToSelect);

		int totalPaxSoFar = Integer.parseInt(adultsToSelect);
		if(totalPaxSoFar > 9){
			handleOverPax();
		}
		Assert.assertTrue(totalPaxSoFar < 9); //causes test to fail if total Adult is over 9

		//Child
		Select child = new Select(dr.findElement(By.xpath("//select[@id='ControlGroupSearchView_AvailabilitySearchInputSearchView_DropDownListPassengerType_CHD']")));
		String childrenToSelect = tooManyPickedStopper().get(1).toString(); //debugging statement to see value
		System.out.println(childrenToSelect); //debugging statement to see value
		child.selectByVisibleText(childrenToSelect); // --> Here is where the above 9 pax error comes up...

		totalPaxSoFar += Integer.parseInt(childrenToSelect);
		if(totalPaxSoFar > 9){
			handleOverPax();
		}
		Assert.assertTrue(totalPaxSoFar < 9); //causes test to fail if total Adult+Child is over 9

		//Infant (infants don't count to the total 9 allowed online)
		Select infant = new Select(dr.findElement(By.xpath("//select[@id='ControlGroupSearchView_AvailabilitySearchInputSearchView_DropDownListPassengerType_INFANT']")));
		String infantsToSelect = tooManyPickedStopper().get(2).toString(); //debugging statement to see value
		System.out.println(infantsToSelect); //debugging statement to see value
		infant.selectByVisibleText(infantsToSelect);

		if(Integer.parseInt(adultsToSelect) < Integer.parseInt(infantsToSelect)){
			handleOverPax();
		}
		Assert.assertTrue(Integer.parseInt(adultsToSelect) > Integer.parseInt(infantsToSelect)); //causes test to fail if number of infants are greater than number of adults
	}
}