package com.wallethub.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage{
	
	protected WebDriver driver;
	
	public BasePage(WebDriver driver) {
		this.driver= driver;
	}
	
	public static final int waitTime = 30;
	
	protected WebElement find (By locator) {
		return driver.findElement(locator);
	}
	
	protected void type (By locator, String text) {
		find(locator).clear();
		find(locator).sendKeys(text);
	}
	
	protected void click (By locator) {
		find(locator).click();
	}
	
	protected void waitToBeClickable (By locator) {
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	protected void waitToBePresent (By locator) {
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	protected void waitToBeInvisible (By locator) {
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}
	
	protected void hoverOnElement (By locator) {
		Actions action = new Actions(driver);
		action.moveToElement(find(locator)).perform();
	}
	
	protected void dropdownOption (By locator, String text) {
		new Select(find(locator)).selectByVisibleText(text);
	}

}
