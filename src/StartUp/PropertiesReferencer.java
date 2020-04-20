package StartUp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReferencer {
	public static Properties pro = new Properties();
	public static FileInputStream props() throws IOException {
		FileInputStream proFile = new FileInputStream("/Users/fida10/Documents/Lynda Hackalicious exercise files/JavaPractice/SpiceJetTestNG/src/globalAccesProps.properties");
		return proFile;
	}
}
