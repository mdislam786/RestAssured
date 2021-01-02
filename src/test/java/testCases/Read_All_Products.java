package testCases;

import static io.restassured.RestAssured.given;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Read_All_Products {
	

	@Test
	public void get_All_Products() {

		Response response = 
				given()
					.baseUri("https://techfios.com")
					.header("Content-Type", "application/json")
				.when()
					.get("/api-prod/api/product/read.php")
				.then()					
					.extract().response();
		
		String responseBody = response.getBody().asString();
//		System.out.println("Response Body: " + "\n" + responseBody);
		
//		Parsing String to json
			JsonPath js = new JsonPath(responseBody);
			js.prettyPrint();
		
		// Validating Status code
		int rsponseStatus = response.getStatusCode();
		System.out.println("Status Code: " + "\n" + rsponseStatus);
		Assert.assertEquals(rsponseStatus, 200);

		// Validating Status code
		long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println(responseTime);
		
		if (responseTime <= 2000) {
				System.out.println("Response time is within range.");
			} else {
				System.out.println("Response time not acceptable!");
			}

	}



}
