package com.Assignment;

import java.util.List;

import org.testng.annotations.Test;

import groovy.transform.ASTTest;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ResponseBodyData;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class GetRequestExampleTwo {


	//	@Test
	public void getRequest() {

		Response getResponse = RestAssured.get("https://reqres.in/api/users/119/");
		ResponseBody responseBody = getResponse.body();
		System.out.println("responseBody.asPrettyString()-->");
		System.out.println(responseBody.asPrettyString());
		//	System.out.println("responseBody.asString()--->");		
		//	System.out.println(responseBody.asString());

	}

	//@Test
	public void anotherExample() {

		RestAssured.baseURI="https://reqres.in/api/";
		ValidatableResponse response  =RestAssured
				.given()
				.param("", "")

				.when()		
				.get("/users/2")
				.then()
				.statusCode(200);
		//	System.out.println("response-->"+ ((ResponseBodyData)response).asPrettyString());

	}




	@Test
	public void JsonPathUsage()  	{
		RestAssured.baseURI = "https://reqres.in/api/users";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("");

		
		JsonPath jsonPathEvaluator = response.jsonPath();

		List<String> names = jsonPathEvaluator.getList("data.first_name");
		for(String name : names)
		{
			System.out.println("Name: " + name);
		}
	}




}

