package com.wallethub.util;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.wallethub.pages.LoginPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	public static ExtentReports reports;
	public static ExtentHtmlReporter htmlReporter;
	private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> testInfo = new ThreadLocal<ExtentTest>();
	private static OptionsManager optionsManager = new OptionsManager();
	public static WebDriver driver;
	protected LoginPage loginPage;
	
	private final String url = System.getProperty("url", "https://wallethub.com");
	
	
	@BeforeSuite
	@Parameters({ "groupReport", "device" })
	public void setUp(String groupReport, String device) throws Exception {
		htmlReporter = new ExtentHtmlReporter(new File(System.getProperty("user.dir") + groupReport));
		reports = new ExtentReports();
		reports.setSystemInfo("TEST ENVIRONMENT", url);
		reports.setSystemInfo("Test Device", device);
		reports.attachReporter(htmlReporter);
	}
	
	@Parameters({ "browser", "device"})
	@BeforeClass
	public void beforeClass(String browser, String device) throws Exception {

		ExtentTest parent = reports.createTest(getClass().getName());
		parentTest.set(parent);
		
		if (device.equalsIgnoreCase("desktop")) {
			if (browser.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver(optionsManager.getChromeOptions());
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				driver.get(url);
				loginPage = new LoginPage(driver);

			} else if (browser.equalsIgnoreCase("opera")) {
				WebDriverManager.operadriver().mac().setup();
				driver= new OperaDriver();
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				driver.get(url);
			}
		}
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
	
	@BeforeMethod(description = "fetch test cases name")
	public void register(Method method) throws InterruptedException {

		ExtentTest child = parentTest.get().createNode(method.getName());
		testInfo.set(child);
		testInfo.get().getModel().setDescription(TestUtils.checkBrowser());

	}

	@AfterMethod(description = "to display the result after each test method")
	public void captureStatus(ITestResult result) throws IOException {
		
		for (String group : result.getMethod().getGroups())
			testInfo.get().assignCategory(group);
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshotPath = TestUtils.addScreenshot();
			testInfo.get().addScreenCaptureFromBase64String(screenshotPath);
			testInfo.get().fail(result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP)
			testInfo.get().skip(result.getThrowable());
		else
			testInfo.get().pass(result.getName() + " Test passed");
		reports.flush();
	}
	
}
