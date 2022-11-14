package com.wallethub.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FacebookPage extends BasePage{
	private By loginButton = By.name("login");
	private By usernameField = By.id("email");
	private By passwordField = By.id("pass");
	private By loginPageContent = By.xpath("//*[@id=\"content\"]/div/div/div/div[1]/h2");

	public FacebookPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 *  Navigate to profile url
	 */
	public void facebookPageUrl(String url) {
		driver.get(url);
	}
	
	/**
	 * @return the profile name
	 */
	public WebElement getLoginContent() {
		waitToBePresent(loginPageContent);
		return find(loginPageContent);
	}
	
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		type(usernameField,username);
	}

	/**
	 *  Click on login button
	 */
	public FacebookPage clickLogin() {
		click(loginButton);
		return new FacebookPage(driver);
	}
	
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		type(passwordField,password);
	}

}
