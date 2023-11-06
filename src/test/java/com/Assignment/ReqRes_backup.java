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

	public class ReqRes_backup {

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
			


		}

		//@Test
		public void getSingleUser() {

			RestAssured.baseURI ="http://restapi.adequateshop.com/api/users/11133";
			RequestSpecification requestspecification = RestAssured.given();
			Response response = requestspecification.request(Method.GET);

			String token = "4ec4a6e7-40d5-4d16-8cef-ff659aba2797";
			requestspecification.headers("Authorization",token).contentType(ContentType.JSON);

			System.out.println(response.asPrettyString());


			JsonPath js = response.jsonPath();
			int LoginID = js.get("data.id");
			String LoginName = js.get("data.name");
			String LoginpPicture = js.get("data.profilepicture");
			String LoginEmail = js.get("data.email");
			String Location = js.get("data.location");

			Assert.assertEquals(LoginID,11133);
			Assert.assertEquals(LoginName.equals("Developer"), true , "Does the Response body contains the name");
			Assert.assertEquals(LoginpPicture.equals("http://restapi.adequateshop.com/Media//Images/userimageicon.png"), true , "Does the Response body contains the profile picture");
			Assert.assertEquals(LoginEmail.equals("Developer12@gmail.com"), true , "Does the Response body contains the email");
			Assert.assertEquals(Location.equals("USA"), true , "Does the Response body contains the Location");
		}


	}
