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

public class sample {

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
		System.out.println(response.asPrettyString());
		getAllUser(LoginToken);
		
	}



	public void getAllUser(String LoginToken) {

		RestAssured.baseURI ="http://restapi.adequateshop.com";
		RequestSpecification requestspecification = RestAssured.given()
				.contentType(ContentType.JSON)
				.header("Authorization","Bearer " +LoginToken);
		Response responseAllUsers = requestspecification.request(Method.GET, "/api/users");
		requestspecification.header("Authorization","Bearer" +LoginToken);
		System.out.println(responseAllUsers.asPrettyString());
		
		JsonPath jsonPathEvaluator = responseAllUsers.jsonPath();
		List<String> names = jsonPathEvaluator.getList("data.name");
		for(String name : names)
		{
			System.out.println("Name: " + name);
		}
		
		

	}
}
