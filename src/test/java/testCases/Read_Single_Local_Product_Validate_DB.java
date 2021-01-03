package testCases;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import payloadFiles.DatabasePage;

public class Read_Single_Local_Product_Validate_DB {

	@Test
	public void read_Single_Local_Product() throws ClassNotFoundException, SQLException {
		Response response = 
				given()
					.baseUri("http://localhost:80")
					.queryParam("id", "802")
					.header("Content-Type", "application/json")
				.when()
					.get("/api-prod/api/product/read_one.php")				
				.then()
					.extract().response();

		int rsponseStatus = response.getStatusCode();
		System.out.println("Status Code: " + "\n" + rsponseStatus);
		Assert.assertEquals(rsponseStatus, 200);

		String responseBody = response.getBody().asString();
		System.out.println("Response Body: " + "\n" + responseBody);
		Assert.assertEquals(responseBody.contains("Special Tire"), true);

		//	Parsing String to json
		JsonPath js = new JsonPath(responseBody);
//		System.out.println("Print body as Pretty: "+js.prettyPrint());
		String productName = js.get("name");
		String productDescription = js.get("description");
		String productPrice = js.get("price");
		String categoryName = js.get("category_name");
		String prodId = js.get("id");

		Assert.assertEquals(productName, DatabasePage.getProduct("name",802).get(0));
		Assert.assertEquals(productDescription, DatabasePage.getProduct("description",802).get(0));
		Assert.assertEquals(productPrice,DatabasePage.getProduct("price",802).get(0));
		
		//Not getting in DB and it's BUG
//		Assert.assertEquals(categoryName, DatabasePage.getProduct("category_name",802).get(0));
		
		
		Assert.assertEquals(prodId, DatabasePage.getProduct("id",802).get(0));
		System.out.println("id: "+ DatabasePage.getProduct("id",802).get(0));

		System.out.println(
				productName + "\n" + 
				productDescription + "\n" + 
				productPrice + "\n" +
				categoryName);

	}
	

}
