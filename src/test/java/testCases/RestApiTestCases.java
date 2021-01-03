package testCases;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import payloadFiles.DatabasePage;

//import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class RestApiTestCases {
	
	
	

	

	

	@Test
	public void get_All_Local_Products() throws ClassNotFoundException, SQLException {

		Response response = 
				given()
					.baseUri("http://localhost:80")
					.header("Content-Type", "application/json")
				.when()
					.get("/api-prod/api/product/read.php")
				.then()					
					.extract().response();
		
		String responseBody = response.asString();
		System.out.println("Response Body: " + "\n" + responseBody);
		
//***********Parsing String to json***********
		
			JsonPath js = new JsonPath(responseBody);
//			js.getList("name");
			String productName = js.get("name");
			String productDescription = js.get("description");
			String productPrice = js.get("price");
			String categoryName = js.get("category_name");

//			Assert.assertEquals(productName, DatabasePage.getProduct("name",802).get(0));
//			Assert.assertEquals(productDescription, DatabasePage.getProduct("description",802).get(0));
//			Assert.assertEquals(productPrice,DatabasePage.getProduct("price",802).get(0));
//			Assert.assertEquals(categoryName,DatabasePage.getProduct("category_name",802).get(0));

			System.out.println(
					js.get("name")
					+ "\n" + 
					productDescription + "\n" + 
					productPrice + "\n" +
					categoryName);

		Assert.assertEquals(responseBody.contains("E-Pillow 3.0"), true);
		
		
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

//		Assert.assertEquals(productName, DatabasePage.getProduct("name",802).get(0));
//		Assert.assertEquals(productDescription, DatabasePage.getProduct("description",802).get(0));
//		Assert.assertEquals(productPrice,DatabasePage.getProduct("price",802).get(0));
//		Assert.assertEquals(categoryName, DatabasePage.getProduct("category_name",802).get(0));

		System.out.println(
				productName + "\n" + 
				productDescription + "\n" + 
				productPrice + "\n" +
				categoryName);

	}

	
	



	
	
	@Test
	public void update_Single_Product() {
		HashMap payload=new HashMap();
		payload.put("id", "912"); 
		payload.put("name", "HP Laptop Elite Pro 3.0");
		payload.put("description", "Super blazing fast laptop");
		payload.put("price", "1399"); 
		payload.put("category_id", "2"); 
		

		
		Response response = 
				given()
					.baseUri("https://techfios.com")
					.header("Content-Type", "application/json")
					.body(payload)
				.when()
					.put("/api-prod/api/product/create.php")
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
	Assert.assertEquals(responseBody.contains("Product was updated."), true);
	
	}
	
	/*
	
	
	@Test(priority=2) 
	public void test_addNewVideoGame() { 
		HashMap data=new HashMap();
		data.put("id", "100"); 
		data.put("name", "Spider-Man");
		data.put("releaseDate", "2019-09-20T08:55:58.510Z"); 
		data.put("reviewScore",	"5"); 
		data.put("category", "Adventure");
		data.put("rating", "Universal");
	
	Response res= 
			given() 
			.contentType("application/json") 
			.body(data) 
			.when()
			.post("http://localhost:8081/app/videogames")	
			.then() 
			.statusCode(200)
			.log()
			.body() 
			.extract().response();
	
	String jsonString=res.asString();
	Assert.assertEquals(jsonString.contains("Record Added Successfully"), true);
	
	}
	
	@Test(priority=1) public void test_getAllVideoGames() {
		Response res =
				given()	
					.baseUri("")
				.when() 
					.get("http://localhost:8081/app/videogames")	
				.then() 
					.statusCode(200)
					.header("content-type", "application/json")
					.extract().response();
		}
	
	
	
	
	@Test(priority=3) 
	public void test_getVideoGame(){ 
		given() 
		.when()
		.get("http://localhost:8081/app/videogames/100") 
		.then() 
			.statusCode(200)
			.log().body() 
			.body("videoGame.id", equalTo("100")) 
			.body("videoGame.name",equalTo("Spider-Man"));
	
	}
	
	@Test(priority=4) public void test_UpdateVideoGame() { 
		HashMap data=new HashMap(); 
		data.put("id", "100"); 
		data.put("name", "Pacman");
		data.put("releaseDate", "2019-09-20T08:57:58.510Z"); 
		data.put("reviewScore",	"4");
		data.put("category", "Adventure"); 
		data.put("rating", "Universal");
	
		given() 
			.contentType("application/json") 
			.body(data) 
		.when()	
			.put("http://localhost:8081/app/videogames/100") 
		.then() .statusCode(200)
		.log().body() 
		.body("videoGame.id", equalTo("100")) 
		.body("videoGame.name", equalTo("Pacman"));
	
	}
	
	@Test(priority=5) public void test_DeleteVideoGame() throws
	InterruptedException { Response res= given() .when()
	.delete("http://localhost:8081/app/videogames/100") .then() .statusCode(200)
	.log().body() .extract().response();
	
	Thread.sleep(3000); String jsonString=res.asString();
	
	Assert.assertEquals(jsonString.contains("Record Deleted Successfully"),
	true); }
	*/ 

}
