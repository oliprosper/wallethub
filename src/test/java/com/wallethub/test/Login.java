package com.wallethub.test;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wallethub.pages.ProfilePage;
import com.wallethub.util.BaseTest;
import com.wallethub.util.TestUtils;

public class Login extends BaseTest {
	private JSONParser parser = new JSONParser();
	private JSONObject config,loginData;
    private String email;
    private String password;
    private String wrongEmail;
    private String fullName;
	
    @BeforeMethod
    public void init() throws IOException, ParseException {
        String path = "src/test/resources/data.conf.json";
        config = (JSONObject) parser.parse(new FileReader(path));
        loginData = (JSONObject) config.get("login");
        
        email = (String) loginData.get("email");
        password = (String) loginData.get("password");
        wrongEmail = (String) loginData.get("wrongEmail");
        fullName = (String) loginData.get("fullName");
    }

	@Test
	private void invalidLogin() {
		TestUtils.testTitle("To navigate on login page and supply usernam <b>"+wrongEmail+"</b> and password <b>"+ password);
		loginPage.clickLoginPageButton();
		loginPage.setUsername(wrongEmail);
		loginPage.setPassword(password);
		loginPage.clickLogin();
		TestUtils.assertText(loginPage.getInvalidLoginErrorMessage(), "Invalid Email or Password");
		
	}
	
	@Test 
	public void validLogin() {
		TestUtils.testTitle("To navigate on login page and supply username <b>"+email+"</b> and password <b>"+ password);
		loginPage.setUsername(email);
		loginPage.setPassword(password);
		ProfilePage profilePage = loginPage.clickLogin();
		TestUtils.assertText(profilePage.getProfileName(), fullName);
		
	}
}
