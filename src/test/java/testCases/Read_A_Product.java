package testCases;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Read_A_Product {
	
	@Test
	public void read_Single_Product() {
		Response response = 
				given()
					.log().all()
					.baseUri("https://techfios.com")
					.queryParam("id", "888")
					.header("Content-Type", "application/json")
				.when()
					.get("/api-prod/api/product/read_one.php")				
				.then()
					.extract().response();				
				
				
//		int rsponseStatus = response.getStatusCode();
//		System.out.println("Status Code: " + "\n" + rsponseStatus);
//		Assert.assertEquals(rsponseStatus, 200);
//
		String responseBody = response.getBody().asString();
//		System.out.println("Response Body: " + "\n" + responseBody);
		Assert.assertEquals(responseBody.contains("E-Pillow 3.0"), true);
		
		
//		Headers res1 = response.getHeaders();
//		System.out.println(res1);


//	Parsing String to json
		JsonPath js = new JsonPath(responseBody);
		js.prettyPrint();
		String productName = js.get("name");
		String productDescription = js.get("description");
		String productPrice = js.get("price");
		String categoryName = js.get("category_name");

		//Validating the response body which was created through POST method
		Assert.assertEquals(productName, "E-Pillow 3.0");
		Assert.assertEquals(productDescription, "The best pillow for amazing programmers.");
		Assert.assertEquals(productPrice, "199");
		Assert.assertEquals(categoryName, "Electronics");

//		System.out.println(
//				productName + "\n" + 
//				productDescription + "\n" + 
//				productPrice + "\n" +
//				categoryName);
//
	}

}
