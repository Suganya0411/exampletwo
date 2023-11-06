package com.Assignment;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.Test;

import groovy.lang.GString;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class GetRequest {

	
	@Test (priority = 1)
	public void userLogin() {
		RestAssured.baseURI ="https://reqres.in/api/users?page=2";
		
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
		System.out.println(response.statusLine());
		Headers allHeaders = response.headers(); 

		for(Header header : allHeaders) { 
			System.out.println("Key: " + header.getName() + " Value: " + header.getValue()); 
		} 


		//validate the response body	
		JsonPath js = response.jsonPath();
		String token = js.get("data.Token");
		System.out.println(token);
	
	}
	
	
	
	//@Test (priority = 2)


		/*
		//validate the response body	
		JsonPath js = response.jsonPath();
		int valueLoginID = js.get("data.id");
		String valueLoginName = js.get("data.name");
		String valueLoginpPicture = js.get("data.profilepicture");
		String valueLoginEmail = js.get("data.email");
		String valueLocation = js.get("data.location");

		Assert.assertEquals(valueLoginID,5);
		Assert.assertEquals(valueLoginName.equals("Charles"), true , "Does the Response body contains the name");
		Assert.assertEquals(valueLoginpPicture.equals("Morris"), true , "Does the Response body contains the profile picture");
		Assert.assertEquals(valueLoginEmail.equals("charles.morris@reqres.in"), true , "Does the Response body contains the email");
		Assert.assertEquals(valueLocation.equals("https://reqres.in/img/faces/5-image.jpg"), true , "Does the Response body contains the Location");
*/
		
	}







