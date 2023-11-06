package com.Assignment;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UsersAPI {

	@Test (priority = 1)
	public void userLogin() {
		RestAssured.baseURI ="http://restapi.adequateshop.com";

		RequestSpecification requestspecification = 
				RestAssured.given()
				.contentType(ContentType.JSON)
				.body("{\r\n"
						+ "  \"email\": \"suhanya@gmail.com\",\r\n"
						+ "  \"password\": \"password\"\r\n"
						+ "}");

		Response response = requestspecification.request(Method.POST,"/api/AuthAccount/Login");

		//validate the response body	
		JsonPath js = response.jsonPath();
		String LoginToken = js.get("data.Token");
		//getAllUser(LoginToken);
		//getSingleUser(LoginToken);
		editUser(LoginToken);
		deleteUser(LoginToken);
	}



	public void getAllUser(String LoginToken) {

		RestAssured.baseURI ="http://restapi.adequateshop.com";
		RequestSpecification requestspecification = RestAssured.given()
				.contentType(ContentType.JSON)
				.header("Authorization","Bearer " +LoginToken);
		Response responseAllUsers = requestspecification.request(Method.GET, "/api/users");
		requestspecification.header("Authorization","Bearer" +LoginToken);
		System.out.println(responseAllUsers.asPrettyString());

		//validate the status code		
		int statusCode = responseAllUsers.getStatusCode();
		Assert.assertEquals(statusCode, 200 );

		//validate the response header		
		Headers allHeaders = responseAllUsers.headers(); 
		for(Header header : allHeaders) { 
			System.out.println("Key: " + header.getName() + " Value: " + header.getValue()); 
		} 

		// validate response body
		JsonPath jsonPathEvaluator = responseAllUsers.jsonPath();
		List<String> names = jsonPathEvaluator.getList("data.name");
		for(String name : names)
		{
			System.out.println("Name: " + name);
		}


	}

	public void getSingleUser(String LoginToken) {

		RestAssured.baseURI ="http://restapi.adequateshop.com";
		RequestSpecification requestspecification = RestAssured.given()
				.contentType(ContentType.JSON)
				.header("Authorization","Bearer " +LoginToken);
		Response response = requestspecification.request(Method.GET,"/api/users/11133");
		requestspecification.header("Authorization","Bearer" +LoginToken);
		System.out.println(response.asPrettyString());
		//validate the status code		
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200 );

		//validate the response header		
		Headers allHeaders = response.headers(); 
		for(Header header : allHeaders) { 
			System.out.println("Key: " + header.getName() + " Value: " + header.getValue()); 
		} 
		//validate response body
		JsonPath js = response.jsonPath();
		int LoginID = js.get("id");
		String LoginName = js.get("name");
		String LoginpPicture = js.get("profilepicture");
		String LoginEmail = js.get("email");
		String Location = js.get("location");

		Assert.assertEquals(LoginID,11133);
		Assert.assertEquals(LoginName.equals("Developer"), true , "Does the Response body contains the name");
		Assert.assertEquals(LoginpPicture.equals("http://restapi.adequateshop.com/Media//Images/userimageicon.png"), true , "Does the Response body contains the profile picture");
		Assert.assertEquals(LoginEmail.equals("Developer12@gmail.com"), true , "Does the Response body contains the email");
		Assert.assertEquals(Location.equals("USA"), true , "Does the Response body contains the Location");

	}



	public void editUser(String LoginToken) {

		RestAssured.baseURI ="http://restapi.adequateshop.com";
		RequestSpecification requestspecification = RestAssured.given()
				.contentType(ContentType.JSON)
				.header("Authorization","Bearer " +LoginToken)
				.body("{\r\n"
						+ "    \"id\": 11133,\r\n"
						+ "    \"name\": \"Developer-NEW\",\r\n"
						+ "    \"email\": \"Deveops@gmail.com\",\r\n"
						+ "    \"profilepicture\": \"http://restapi.adequateshop.com/Media//Images/userimageicon.png\",\r\n"
						+ "    \"location\": \"USA\",\r\n"
						+ "    \"createdat\": \"0001-01-01T00:00:00\"\r\n"
						+ "}");

		Response response = requestspecification.request(Method.PUT,"/api/users/11133");
		requestspecification.header("Authorization","Bearer" +LoginToken);
		System.out.println("-----------------------editUser-----------------------");
		System.out.println(response.asPrettyString());
		System.out.println("-----------------------editUser-----------------------");
		//validate the status code		
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 405 );
		//validate the response header		
		Headers allHeaders = response.headers(); 
		for(Header header : allHeaders) { 
			System.out.println("Key: " + header.getName() + " Value: " + header.getValue()); 
		} 
	}


	public void deleteUser(String LoginToken) {

		RestAssured.baseURI ="http://restapi.adequateshop.com";
		RequestSpecification requestspecification = RestAssured.given()
				.contentType(ContentType.JSON)
				.header("Authorization","Bearer " +LoginToken);
		Response response = requestspecification.request(Method.DELETE,"/api/users/11133");
		requestspecification.header("Authorization","Bearer" +LoginToken);
		System.out.println("-----------------------Delete User-----------------------");
		System.out.println(response.asPrettyString());
		System.out.println("-----------------------Delete User-----------------------");
		//validate the status code		
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 405 );
		//validate the response header		
		Headers allHeaders = response.headers(); 
		for(Header header : allHeaders) { 
			System.out.println("Key: " + header.getName() + " Value: " + header.getValue()); 
		} 
	}



}