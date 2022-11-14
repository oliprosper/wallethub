package com.wallethub.test;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wallethub.pages.ProfilePage;
import com.wallethub.util.BaseTest;
import com.wallethub.util.TestUtils;

public class Profile extends BaseTest{
	protected ProfilePage profilePage;
	private JSONParser parser = new JSONParser();
	private JSONObject config,loginData,profileData;
    private String email;
    private String password;
    private String profilePageUrl;
    private String company;
    private String policyOption;
    private String reviewer;
	
    @BeforeMethod
    public void init() throws IOException, ParseException {
        String path = "src/test/resources/data.conf.json";
        config = (JSONObject) parser.parse(new FileReader(path));
        loginData = (JSONObject) config.get("login");
        profileData = (JSONObject) config.get("profile");
        
        email = (String) loginData.get("email");
        password = (String) loginData.get("password");
        profilePageUrl = (String) profileData.get("url");
        company = (String) loginData.get("company");
        policyOption = (String) profileData.get("policyOption");
        reviewer = (String) profileData.get("reviewer");
    }
	
	@Test(priority = 0)
	private void navigateToProfilePage() throws InterruptedException {
		profilePage = new ProfilePage(driver);
		
		// Navigate to profile url
		profilePage.profilePageUrl(profilePageUrl);
		
		TestUtils.testTitle("To redirect to url: "+profilePageUrl);
		
		// Assert company name after login
		TestUtils.assertText(profilePage.getProfileName(), company);
		
	}
	
	@Test(priority = 1)
	private void hoverAndClickOnFourthStar() throws InterruptedException {
		// scroll to review section
		TestUtils.scrollToElement(profilePage.getRating());
		
		// hover on rating fourth star
		TestUtils.testTitle("To verify that 4th star get lit up when user hover over them: ");
		profilePage.hoverOnFourthStar();
		
		// Assert that fourth star aria-checked element attribute change from false to true
		Assert.assertEquals(profilePage.getFourthStar().getAttribute("aria-checked"), "true");
		testInfo.get().info("4th star get lit up");
		
		// Click on 4th star rating
		TestUtils.testTitle("To verify that user is redirect to enter new review page when click on a review rating");
		profilePage.getFourthStar().click();
		
		// Assert that fourth star aria-checked element attribute change from false to true
		Assert.assertEquals(profilePage.getFourthStar().getAttribute("aria-checked"), "true");
		testInfo.get().info("4th star get lit up after navigating to review page.");
		
	}
	
	@Test(priority = 3)
	private void submitReview() throws InterruptedException {
		// Select Health insurance in drop down option
		TestUtils.testTitle("To verify that user can select an option in policy options");
		profilePage.selectOption();
		profilePage.selectHealthInsurance();
		TestUtils.assertText(profilePage.getSelectedPolicyOption(), policyOption);
		
		// Enter review below accepted character length title
		TestUtils.testTitle("To verify that character count is highligted in red when entered character is below accepted character length");
		
		// Enter random text of 15 characters in write review field
		String reviewText1 = TestUtils.generateRandomCharacters(15,true,false);
		profilePage.setReview(reviewText1);
		
		// Assert that character count value is red
		int characterCount = TestUtils.convertToInt(profilePage.invalidCharacterCountLimit().getText());
		Assert.assertEquals(characterCount, reviewText1.length());
		testInfo.get().info("Character count: "+ characterCount +" is in color-red and is equal to the characters entered " +reviewText1.length());
		
		// Enter review below accepted character length title
		TestUtils.testTitle("To verify that character count is highligted in color-aqua when entered character is in accepted character length");
				
		// Enter random text of 200 characters in write review field
		String reviewText2 = TestUtils.generateRandomCharacters(200,true,false);
		profilePage.setReview(reviewText2);
		
		// Assert that character count value is red
		int characterCount2 = TestUtils.convertToInt(profilePage.validCharacterCountLimit().getText());
		Assert.assertEquals(characterCount2, reviewText2.length());
		testInfo.get().info("Character count: "+ characterCount2 +" is in color-aqua and is equal to the characters entered " +reviewText2.length());
		
		// Review submission title
		TestUtils.testTitle("To verify that user is able to submit review successfully");
		
		//Click on submit button
		profilePage.submitReview();
//		TestUtils.assertText(profilePage.getAwesomeText(), "Awesome!");
//		TestUtils.assertText(profilePage.getSuccessMessageText(), "submitted");
		
		// Login back to see add reviews
		profilePage.clickLoginPageButton();
		TestUtils.testTitle("To navigate on login page and supply username <b>"+email+"</b> and password <b>"+ password);
		profilePage.setUsername(email);
		profilePage.setPassword(password);
		profilePage.clickLogin();
		TestUtils.assertText(profilePage.getProfileName(), company);
		
		// Review submission title
		TestUtils.testTitle("To assert that user can see successfully added review");
		
		// Scroll to review sorting view and assert review
		TestUtils.scrollToElement(profilePage.sortingSection());
		TestUtils.assertText(profilePage.getMyReview(reviewer), reviewText2);
		
		// Take screenshot and attach to html report
		String screenshotPath = TestUtils.addScreenshot();
		testInfo.get().addScreenCaptureFromBase64String(screenshotPath);
		
		
	}

}
