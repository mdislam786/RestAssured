package testCases;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import io.restassured.response.Response;

public class Create_A_Product_And_Validate_In_Database {
	
	@Test
	public void create_Single_Product() {
		HashMap payload=new HashMap();
//		payload.put("id", "10001"); 
		payload.put("name", "HP Laptop Elite Pro 7.0");
		payload.put("description", "Super fast laptop");
		payload.put("price", "1299"); 
		payload.put("category_id", "2"); 
		
		Response response = 
				given()
					.baseUri("https://techfios.com")
					.header("Content-Type", "application/json")
					.body(payload)
				.when()
					.post("/api-prod/api/product/create.php")
				.then()
					.log().body()
					.extract().response();
		
		
		// Validating Status code
		int rsponseStatus = response.getStatusCode();
		System.out.println("Status Code: " + "\n" + rsponseStatus);
		Assert.assertEquals(rsponseStatus, 201);

		// Validating Status code
		long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println("Response Time: "+ "\n"+responseTime);
		
		if (responseTime <= 2000) {
				System.out.println("Response time is within range.");
			} else {
				System.out.println("Response time not acceptable!");
			}
	
	
	String responseBody = response.asString();
	System.out.println("Response Body: " + "\n" + responseBody);
	Assert.assertEquals(responseBody.contains("Product was created."), true);
	
	}
	

}
