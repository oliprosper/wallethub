package com.wallethub.test;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wallethub.pages.FacebookPage;
import com.wallethub.util.BaseTest;
import com.wallethub.util.TestUtils;

public class Facebook extends BaseTest{
	protected FacebookPage facebookPage;
	private JSONParser parser = new JSONParser();
	private JSONObject config,facebookData;
    private String email;
    private String password;
    private String facebookUrl;
	
    @BeforeMethod
    public void init() throws IOException, ParseException {
        String path = "src/test/resources/data.conf.json";
        config = (JSONObject) parser.parse(new FileReader(path));
        facebookData = (JSONObject) config.get("facebook");
        
        email = (String) facebookData.get("email");
        password = (String) facebookData.get("password");
        facebookUrl = (String) facebookData.get("url");
    }
	
	@Test(priority = 0)
	private void navigateToProfilePage() throws InterruptedException {
		facebookPage = new FacebookPage(driver);
		
		// Navigate to facebook url
		facebookPage.facebookPageUrl(facebookUrl);
		
		TestUtils.testTitle("To redirect to url: "+facebookUrl);
		
		// Assert Facebook content on login page
		TestUtils.assertText(facebookPage.getLoginContent(), "Facebook helps you connect and share with the people in your life.");
		
	}
	
	@Test(priority = 1)
	private void login() throws InterruptedException {
		TestUtils.testTitle("To navigate on login page and supply username <b>"+email+"</b> and password <b>"+ password);
		facebookPage.setUsername(email);
		facebookPage.setPassword(password);
		facebookPage.clickLogin();
	}

}
