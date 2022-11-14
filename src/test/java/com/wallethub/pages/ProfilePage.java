package com.wallethub.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProfilePage extends BasePage{
	
	private By profileName = By.cssSelector(".profile-name");
	private By editProfileBtn = By.xpath("//*[@id=\"scroller\"]/main/div[1]/div/div[3]/button");
	private By ratingSection = By.cssSelector(".rsba-h3.bold-font");
	private By fourthStar = By.xpath("(//*[name()='svg'][@aria-label='4 star rating'])[3]");
	private By fourthStarReviewPage = By.xpath("(//*[name()='svg'][@aria-label='4 star rating'])[14]");
	private By policyOption = By.xpath("//section[@id='reviews-section']/modal-dialog/div/div/write-review/div[2]/div/ng-dropdown/div");
	private By selectedOption = By.cssSelector("ng-dropdown.wrev-drp > div.dropdown.second.selected > span.dropdown-selected");
	private By healthInsurance = By.xpath("//li[contains(text(),'Health Insurance')]");
	private By reviewInputField = By.xpath("//*[@id=\"reviews-section\"]/modal-dialog/div/div/write-review/div[2]/div/div[1]/textarea");
	private By reviewCharacterValidCount = By.cssSelector(".bold-font.color-aqua");
	private By reviewCharacterInvalidCount = By.cssSelector(".bold-font.color-red");
	private By reviewSubmitBtn = By.cssSelector("div.sbn-action.semi-bold-font.btn.fixed-w-c.tall");
	private By awesomeLabel = By.cssSelector("div[class='rvc-header'] h2");
	private By successMessage = By.cssSelector("div[class='rvc-header'] h4");
	private By mostRecentOption = By.cssSelector("span[aria-label='Most Recent'] span:nth-child(2)");
	private By loginTab = By.xpath("//*[@id=\"join-light\"]/form/div/nav/ul/li[2]/a");
	private By loginButton = By.xpath("//*[@id=\"join-light\"]/form/div/div[3]/button");
	private By usernameField = By.id("em-ipt");
	private By passwordField = By.id("pw1-ipt");

	public ProfilePage (WebDriver driver) {
		super(driver);
	}
	
	/**
	 *  Navigate to profile url
	 */
	public void profilePageUrl(String url) {
		driver.get(url);
	}
	
	/**
	 * @return the profile name
	 */
	public WebElement getProfileName() {
		waitToBePresent(profileName);
		return find(profileName);
	}
	
	/**
	 *  Click on login button
	 */
	public void clickEditBtn() {
		click(editProfileBtn);
	}
	
	/**
	 * @return What's Your Rating? element  
	 */
	public WebElement getRating() {
		waitToBePresent(ratingSection);
		return find(ratingSection);
	}
	
	/**
	 * @return the 4th star element
	 */
	public WebElement getFourthStar() {
		waitToBePresent(fourthStar);
		return find(fourthStar);
	}
	
	/**
	 * @return the 4th star element
	 */
	public WebElement getFourthStarReviewPage() {
		waitToBePresent(fourthStarReviewPage);
		return find(fourthStarReviewPage);
	}
	
	/**
	 *  Hover on 4th Star in rating
	 */
	public void hoverOnFourthStar() {
		hoverOnElement(fourthStar);
	}
	
	/**
	 *  Click policy option in drop down
	 */
	public void selectOption() {
		click(policyOption);
	}
	
	/**
	 *  Select health insurance option in drop down
	 */
	public void selectHealthInsurance() {
		waitToBeClickable(healthInsurance);
		click(healthInsurance);
	}
	
	/**
	 * @return the profile name
	 */
	public WebElement getSelectedPolicyOption() {
		waitToBePresent(selectedOption);
		return find(selectedOption);
	}
	
	/**
	 * @param Enter user review
	 */
	public void setReview(String reivew) {
		type(reviewInputField,reivew);
	}
	
	/**
	 * @return review characters count element
	 */
	public WebElement validCharacterCountLimit() {
		waitToBePresent(reviewCharacterValidCount);
		return find(reviewCharacterValidCount);
	}
	
	/**
	 * @return review characters count element
	 */
	public WebElement invalidCharacterCountLimit() {
		waitToBePresent(reviewCharacterInvalidCount);
		return find(reviewCharacterInvalidCount);
	}
	
	/**
	 *  Click on review submit button
	 */
	public void submitReview() {
		click(reviewSubmitBtn);
	}
	
	/**
	 * @return the awesome text element
	 */
	public WebElement getAwesomeText() {
		waitToBePresent(awesomeLabel);
		return find(awesomeLabel);
	}
	
	/**
	 * @return the awesome text element
	 */
	public WebElement getSuccessMessageText() {
		waitToBePresent(successMessage);
		return find(successMessage);
	}
	
	/**
	 * @return the review sorting element
	 */
	public WebElement sortingSection() {
		waitToBePresent(mostRecentOption);
		return find(mostRecentOption);
	}
	
	/**
	 * @return the awesome text element
	 */
	public WebElement getMyReview(String reviwer) {
		int reviews = driver.findElements(By.xpath("//*[@id=\"reviews-section\"]/section/article")).size();
		WebElement myReview = null;
		for (int i = 1; i <= reviews; i++) {
			String desc = find(
					By.xpath("//*[@id=\"reviews-section\"]/section/article[" + i + "]/div[2]/div[2]/span[1]"))
					.getText();
			if (desc.equalsIgnoreCase(reviwer)) {
				myReview = find(By.xpath("//*[@id=\"reviews-section\"]/section/article[" + i + "]/div[5]"));

			}
		}
		return myReview;
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
		waitToBePresent(loginTab);
		click(loginTab);
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		type(passwordField,password);
	}

}
