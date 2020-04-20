package FlightOptions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static StartUp.Initializer.dr;
import static StartUp.Initializer.pro;

public class DatePicker {
	public static void departDate(String monthFirstLetterCapital, String year, String date){
		Actions a = new Actions(dr);
		WebElement departDateBox = dr.findElement(By.xpath("//input[@class='custom_date_pic required home-date-input' and @id='custom_date_picker_id_1']"));//dynamic, this is different between before login and after...
		WebElement calendarBox = dr.findElement(By.xpath("//div[@id='ui-datepicker-div']"));
		while(true) { //this loop is in place because sometimes the page desyncs and clicks on the date box when it actually isn't available.
			try {
				if (calendarBox.isDisplayed()) {
					System.out.println("Box is displayed properly");
					break;
				}
			} catch (NoSuchElementException e)
			{
				System.out.println("Box is not displayed, trying to click again");
				a
						.moveToElement(departDateBox)
						.click()
						.build()
						.perform();
			}
		}
		List<String> endOfCalendar = new ArrayList<>();
		endOfCalendar.add("dummyMonthAndYear1");
		endOfCalendar.add("dummyYearAndMonth2");

		while(true){
			String depMonth = dr.findElement(By.xpath("//div[@id='ui-datepicker-div']//div[1]//div[1]//div[1]//span[1]")).getText();
			String depYear = dr.findElement(By.xpath("//div[@id='ui-datepicker-div']//div[1]//div[1]//div[1]//span[2]")).getText();
			if(depMonth.contains(monthFirstLetterCapital) && depYear.contains(year))
			{
				System.out.println("Reached desired month and year.");
				break;
			}
			String currentMonthAndYear = depMonth + " " + depYear;
			endOfCalendar.add(currentMonthAndYear);
			String lastCalendarElement = endOfCalendar.get(endOfCalendar.size()-1);
			String secondToLastCalendarElement = endOfCalendar.get(endOfCalendar.size()-2);
			if(lastCalendarElement.equalsIgnoreCase(secondToLastCalendarElement))
			{
				System.out.println("Reached the end of the calendar, invalid date was selected.");
				Assert.assertFalse(lastCalendarElement.equalsIgnoreCase(secondToLastCalendarElement)); //forces the test to fail if the above statement is true, in other words, if the same month is coming over and over again.
				break;
			}
			a //this clicks on the next month arrow
					.moveToElement(dr.findElement(By.xpath("//div[@id='ui-datepicker-div']//div[2]//div[1]//a[1]")))
					.click()
					.build()
					.perform();
		}
		a
				.moveToElement(dr.findElement(By.xpath(String.format("//div[@class='ui-datepicker-group ui-datepicker-group-first']//table[1]//tbody[1]//a[text()='%s']", date)))) //this clicks on the date you want
				.click()
				.build()
				.perform();
	}
	public static void returnDate(String monthFirstLetterCapital, String year, String date){
	}
	@Test
	public void departDatePickerTest(){
		departDate(pro.getProperty("depMonthFirstLetterCapital"), pro.getProperty("depYear"), pro.getProperty("depDate")); //variables must now be defined in properties
	}
	@AfterClass
	public void exitDatePicker(){ //clicks somewhere else, in this case on where it says "depart date", to exit out of the date box
		Actions a = new Actions(dr);
		a
				.moveToElement(dr.findElement(By.xpath("//label[@for='ControlGroupSearchView_AvailabilitySearchInputSearchView_TextBoxMarketOrigin1']")))
				.click()
				.build()
				.perform();
	}
}
