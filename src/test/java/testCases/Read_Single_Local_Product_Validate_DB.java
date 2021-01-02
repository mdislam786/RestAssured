package testCases;

import static io.restassured.RestAssured.given;

import java.sql.SQLException;

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
					.log().all()
					.baseUri("http://localhost:80")
					.queryParam("id", "802")
					.header("Content-Type", "application/json")
				.when()
					.get("/api-prod/api/product/read_one.php")				
				.then()
					.log().all()
					.statusCode(200)
					.extract().response();

		int rsponseStatus = response.getStatusCode();
		System.out.println("Status Code: " + "\n" + rsponseStatus);
		Assert.assertEquals(rsponseStatus, 200);

		String responseBody = response.asString();
		System.out.println("Response Body: " + "\n" + responseBody);
//		Assert.assertEquals(responseBody.contains("E-Pillow 3.0"), true);

//	Parsing String to json
		JsonPath js = new JsonPath(responseBody);
		System.out.println(js.prettyPrint());
		String productName = js.get("name");
		String productDescription = js.get("description");
		String productPrice = js.get("price");
		String categoryName = js.get("category_name");

		Assert.assertEquals(productName, DatabasePage.getProduct("name",802).get(0));
		Assert.assertEquals(productDescription, DatabasePage.getProduct("description",802).get(0));
		Assert.assertEquals(productPrice,DatabasePage.getProduct("price",802).get(0));
//		Assert.assertEquals(categoryName, DatabasePage.getProduct("category_name",802).get(0));

		System.out.println(
				productName + "\n" + 
				productDescription + "\n" + 
				productPrice + "\n" +
				categoryName);

	}
	

}
