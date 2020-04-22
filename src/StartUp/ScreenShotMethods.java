package StartUp;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static StartUp.Initializer.*;


public class ScreenShotMethods {
	private static final String screenShotSavedDir = getDateInStrAndMkDir();

	private static String programRunCountTracker(){ //keeps track of how many times the program runs, and labels folders accordingly. Seems to iterate each time a test is run. Unacceptable, this must run only once. This needs a fix
		String programRunCountTrackerStringOld = pro.getProperty("programRunCountTracker");
		int programRunCountTrackerInt = Integer.parseInt(programRunCountTrackerStringOld);
		++programRunCountTrackerInt;
		String programRunCountTrackerStringNew = Integer.toString(programRunCountTrackerInt);
		pro.setProperty("programRunCountTracker", String.format("%s", programRunCountTrackerStringNew));
		return programRunCountTrackerStringNew;

		//what this was intended to do: Go into the properties file to the property "programRunCountTracker" and update the value by increasing it by one, each time the program is run. This lets us organize test runs based on the run number. It does not, unfortunately, change the value within the properties folder. There are other ways to do this requiring investigation.
	}
	static String getDateInStrAndMkDir(){
		DateTimeFormatter dtForm = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm a");
		LocalDateTime current = LocalDateTime.now();
		String currentDate = "/" + dtForm.format(current);

		String fileDirToSave = String.format("out//success_fail_screenshots//testDnT%s", currentDate);
		/*
		new method with param String sOrF

		if(sOrF.equalsIgnoreCase("S")){
			fileDirToSave = String.format("out//success_fail_screenshots//test_no%s//Successes%s", programRunCountTracker(), currentDate);
		} else if(sOrF.equalsIgnoreCase("F")){
			fileDirToSave = String.format("out//success_fail_screenshots//test_no%s//Failures%s", programRunCountTracker(), currentDate);
		} else {
			System.out.println("Invalid input, put 'S' for success test, and 'F' for failed test");
		}*/
		boolean fileMadeSuccess = new File(fileDirToSave).mkdir();
		System.out.println("Was the screenshot directory made successfully? " + fileMadeSuccess);
		return fileDirToSave;
	}
	static void screenShotTaker(String passFailSkip) throws IOException {
		File screenShot = ((TakesScreenshot)dr).getScreenshotAs(OutputType.FILE);
		if(passFailSkip.equalsIgnoreCase("pass")){
			FileUtils.copyFileToDirectory(screenShot, new File(screenShotSavedDir + "//success"));
		} else if(passFailSkip.equalsIgnoreCase("fail")){
			FileUtils.copyFileToDirectory(screenShot, new File(screenShotSavedDir + "//failure"));
		} else if(passFailSkip.equalsIgnoreCase("skip")){
			FileUtils.copyFileToDirectory(screenShot, new File(screenShotSavedDir + "//skip"));
		} else {
			FileUtils.copyFileToDirectory(screenShot, new File(screenShotSavedDir + "//unknown"));
		}
	}
	static void deleteOldScreenShots() throws IOException{
		FileUtils.deleteDirectory(new File("out/success_fail_screenshots"));
	}
}
