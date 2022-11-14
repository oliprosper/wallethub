package com.wallethub.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class TestUtils extends BaseTest{

	public static Dimension setDimension(String device) {

		Dimension dimension = null;

		if (device.equalsIgnoreCase("iPhone 8")) {
			dimension = new Dimension(375, 667);

		} else if (device.equalsIgnoreCase("iPhone X")) {
			dimension = new Dimension(375, 812);

		} else if (device.equalsIgnoreCase("iPhone 8 Plus")) {
			dimension = new Dimension(414, 736);

		} else if (device.equalsIgnoreCase("iPad Mini")) {
			dimension = new Dimension(768, 1024);

		} else if (device.equalsIgnoreCase("Galaxy S5")) {
			dimension = new Dimension(360, 640);

		} else if (device.equalsIgnoreCase("Galaxy S9")) {
			dimension = new Dimension(360, 740);

		} else if (device.equalsIgnoreCase("Nexus 5X")) {
			dimension = new Dimension(412, 732);

		} else if (device.equalsIgnoreCase("Galaxy Tab 10")) {
			dimension = new Dimension(800, 1280);

		} else {

			System.out.println(device + " not found");
		}
		return dimension;
	}
	
	public static String checkBrowser() {
		// Get Browser name and version.
		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = caps.getBrowserName();
		String browserVersion = caps.getVersion();

		// return browser name and version.
		String os = browserName + " " + browserVersion;
		return os;
	}
	
	public static String addScreenshot() {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File scrFile = ts.getScreenshotAs(OutputType.FILE);
		String encodedBase64 = null;
		FileInputStream fileInputStreamReader = null;
		try {
			fileInputStreamReader = new FileInputStream(scrFile);
			byte[] bytes = new byte[(int) scrFile.length()];
			fileInputStreamReader.read(bytes);
			encodedBase64 = new String(Base64.encodeBase64(bytes));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "data:image/png;base64," + encodedBase64;
	}
	
	/**
	 * @param element
	 * @throws InterruptedException
	 * @description to scroll to a particular element on the page.
	 */
	public static void scrollToElement(WebElement element) throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView();", element);
		Thread.sleep(200);
	}
	
	/**
	 * @param phrase
	 * @throws InterruptedException
	 * @description to log test subtitle on html report.
	 */
	public static void testTitle(String phrase) {
		String word = "<b>"+phrase+"</b>";
        Markup w = MarkupHelper.createLabel(word, ExtentColor.BLUE);
        testInfo.get().info(w);
	}
	
	/**
	 * @param element
	 * @param expectedValue
	 * @throws InterruptedException
	 * @description to check if the expected text is present in the page using soft assertion which returns error flag if not found.
	 */
	public static void assertText(WebElement element, String expectedValue){

		StringBuffer verificationErrors = new StringBuffer();
		try {
			Assert.assertEquals(element.getText(), expectedValue);
			testInfo.get().log(Status.INFO,"<b>"+ expectedValue + " </b>found");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error("<b>"+ expectedValue + " </b>not found");
			testInfo.get().error(verificationErrorString);
		}
	}
	
	/**
	 * @param value
	 * @return Integer value.
	 * @throws InterruptedException
	 * @description to convert string value to Integer value for calculations
	 */
	public static Integer convertToInt(String value) {
		Integer result = null;
		String convertedString = value.replaceAll("[^0-9]", "");
		try {
			return result = Integer.parseInt(convertedString);
		} catch (NumberFormatException e) {
			testInfo.get().error("convertToInt  Error converting to integer ");
			testInfo.get().error(e);
		}
		return result;

	}
	
	/**
	 * @param length, useLetters, useNumbers
	 * @return String value
	 * @description Generates random characters of specified length, alphabets and/or numbers
	 */
	public static String generateRandomCharacters(int length, boolean useLetters, boolean useNumbers) {
	    String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
		return generatedString.toLowerCase().replaceAll("u", " ");
	}
}
