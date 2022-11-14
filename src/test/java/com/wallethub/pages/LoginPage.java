package com.wallethub.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class LoginPage extends BasePage{
	
	private By loginPageButton = By.xpath("//a[@class='login']");
	private By usernameField = By.id("email");
	private By passwordField = By.id("password");
	private By loginButton = By.cssSelector(".btn.blue.center.reg-tabs-bt.touch-element-cl");
	private By invalidLoginErrorMessage = By.cssSelector(".error.left.ng-animate-enabled.basic-trans.enter[ng-if='$form.em.$error.denied && !errors_refresh']");
	
	public LoginPage (WebDriver driver) {
		super(driver);
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
	public ProfilePage clickLogin() {
		click(loginButton);
		return new ProfilePage(driver);
	}
	
	/**
	 *  Click on login page button on web page
	 */
	public void clickLoginPageButton() {
		click(loginPageButton);
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		type(passwordField,password);
	}
	
	/**
	 * @return the invalid login error message locator
	 */
	public WebElement getInvalidLoginErrorMessage() {
		waitToBePresent(invalidLoginErrorMessage);
		return find(invalidLoginErrorMessage);
	}

	

}
