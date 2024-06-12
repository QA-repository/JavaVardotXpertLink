package src.test.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.xmlbeans.xml.stream.Space;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.System.getProperty;


public class TestBases {


	public static ExtentSparkReporter spark;
	public static ExtentReports extent;


	static String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());


	public void openbrowser(List<String> Logininfo,List<String>cookie,String URL) throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("https://unhcr-staging.unhcr.info/user/login");

		String Cookie= "";
List<String> a=new ArrayList<>();
		String CookieValue = "";
		for (int count = 0; count < cookie.size()-1; count += 2) {
			a.add(cookie.get(count));
			a.add(cookie.get(count+1));

			driver.manage().addCookie(new Cookie(cookie.get(count)+"=", cookie.get(count+1)));

		}


	}
	public static String generateRandomString(int length) {
		String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

		SecureRandom random = new SecureRandom();
		StringBuilder stringBuilder = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			int randomIndex = random.nextInt(CHARACTERS.length());
			char randomChar = CHARACTERS.charAt(randomIndex);
			stringBuilder.append(randomChar);
		}

		return "Automation_Tool_"+stringBuilder.toString();
	}

	@BeforeSuite
	public void setUp() throws IOException {
		extent = new ExtentReports();
		LocalDateTime currentDateTime = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH_mm_ss");

		String formattedDateTime = currentDateTime.format(formatter);
		ConfigurationReader CR=new ConfigurationReader();
//		//ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/" + CR.GetProjectname()+" Project "+formattedDateTime+".html");
//		sparkReporter.config().setDocumentTitle("Vardot E2E Tool Report");
//		sparkReporter.config().setReportName("Vardot E2E Tool Report");
//		sparkReporter.config().setTheme(Theme.DARK); // You can choose from Theme.STANDARD, Theme.DARK, Theme.VIEW
//		extent.attachReporter(sparkReporter);
//		extent.attachReporter(spark);
//		//extent.setSystemInfo("Project", CR.GetProjectname());
//		extent.setSystemInfo("Site link", CR.GetRun_ENV());
//		//extent.setSystemInfo("Environment", CR.ProjectEnv());


	}

	@AfterSuite
	public void tearDown() {
		extent.flush();
	}
}
