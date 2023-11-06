package com.Assignment;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matcher.*;
import static org.testng.Assert.assertEquals;

public class RegistrationAndLogin {

	//@Test (description = "userRegistration API")
	public void userRegistration() {

		RestAssured.baseURI ="http://restapi.adequateshop.com";
		String AuthToken = "Bearer aae2602c-dd13-481a-9e33-fd9b59618d0f";

		RequestSpecification requestspecification = 
				RestAssured.given()
				.headers("Authorization","AuthToken").contentType(ContentType.JSON)
				.body("{\r\n"
						+ "  \"name\": \"Luke\",\r\n"
						+ "  \"email\": \"Luke@gmail.com\",\r\n"
						+ "  \"password\": \"password\"\r\n"
						+ "}");

		Response response = requestspecification.request(Method.POST,"/api/AuthAccount/Registration");

		//Print the output
		System.out.println(response.asPrettyString());
		System.out.println(response.getStatusLine());

		//validate the status code		
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200 );

		//validate the response header		
		Headers allHeaders = response.headers(); 

		for(Header header : allHeaders) { 
			System.out.println("Key: " + header.getName() + " Value: " + header.getValue()); 
		} 

		//validate the response body	
		JsonPath js = response.jsonPath();
		String registerName = js.get("data.Name");
		String registerEmail = js.get("data.Email");
		System.out.println(registerName + " " + registerEmail);
		
		Assert.assertEquals(registerName.equals("Luke"), true , "Does the Response body contains the Name");
		Assert.assertEquals(registerEmail.equals("Luke@gmail.com"), true , "Does the Response body contains the Name");
	

	}


	//@Test (description = "userRegistrationEmailExists API")
	public void userRegistrationEmailExists() {

		RestAssured.baseURI ="http://restapi.adequateshop.com";
		String AuthToken = "Bearer aae2602c-dd13-481a-9e33-fd9b59618d0f";

		RequestSpecification requestspecification = 
				RestAssured.given()
				.headers("Authorization","AuthToken").contentType(ContentType.JSON)
				.body("{\r\n"
						+ "  \"name\": \"Jean\",\r\n"
						+ "  \"email\": \"Jean@gmail.com\",\r\n"
						+ "  \"password\": \"password\"\r\n"
						+ "}");

		Response response = requestspecification.request(Method.POST,"/api/AuthAccount/Registration");

		//Print the output
		System.out.println(response.asPrettyString());
		System.out.println(response.getStatusLine());

		//validate the email already exists message
		ResponseBody body = response.body();
		JsonPath jsUEE = response.jsonPath();
		String message = jsUEE.get("message");
		//System.out.println("message" + message);
		Assert.assertEquals(message.equals("The email address you have entered is already registered"), true , "Error message");

	}	



	@Test
	public void userLogin() {
		RestAssured.baseURI ="http://restapi.adequateshop.com";
		String AuthToken = "Bearer aae2602c-dd13-481a-9e33-fd9b59618d0f";

		RequestSpecification requestspecification = 
				RestAssured.given()
				.headers("Authorization","AuthToken").contentType(ContentType.JSON)
				.body("{\r\n"
						+ "  \"email\": \"suhanya@gmail.com\",\r\n"
						+ "  \"password\": \"password\"\r\n"
						+ "}");

		Response response = requestspecification.request(Method.POST,"/api/AuthAccount/Login");

		//Print the output
		System.out.println(response.asPrettyString());

		//validate the status code		
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200 );

		//validate the response header		
		Headers allHeaders = response.headers(); 

		for(Header header : allHeaders) { 
			System.out.println("Key: " + header.getName() + " Value: " + header.getValue()); 
		} 

		//validate the response body	
		JsonPath js = response.jsonPath();
		int valueLoginID = js.get("data.Id");
		String valueLoginName = js.get("data.Name");
		String valueLoginEmail = js.get("data.Email");

		Assert.assertEquals(valueLoginID,280516);
		Assert.assertEquals(valueLoginName.equals("suhanya"), true , "Does the Response body contains the Name");
		Assert.assertEquals(valueLoginEmail.equals("suhanya@gmail.com"), true , "Does the Response body contains the Email");

	}

	//@Test
	public void userLoginWrongInput() {
		RestAssured.baseURI ="http://restapi.adequateshop.com";
		String AuthToken = "Bearer aae2602c-dd13-481a-9e33-fd9b59618d0f";

		RequestSpecification requestspecification = 
				RestAssured.given()
				.headers("Authorization","AuthToken").contentType(ContentType.JSON)
				.body("{\r\n"
						+ "  \"email\": \"sunya@gmail.com\",\r\n"
						+ "  \"password\": \"password\"\r\n"
						+ "}");

		Response response = requestspecification.request(Method.POST,"/api/AuthAccount/Login");

		//Print the output
		System.out.println(response.asPrettyString());
		System.out.println(response.getStatusLine());

		//validate the response body	
		JsonPath js = response.jsonPath();
		String loginMessage = js.get("message");
		Assert.assertEquals(loginMessage.equals("invalid username or password"), true , "Response body contains the Login error message");



	}



}
