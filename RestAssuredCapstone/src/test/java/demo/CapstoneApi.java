package demo;

import java.util.HashMap;
import java.util.UUID;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CapstoneApi {
	
	HashMap<String, String> map = new HashMap<>();
	int id;
	UUID uuid = UUID.randomUUID();
	
	
	
	@BeforeTest
	public void createPayLoad() {
		map.put("name", "Abdl");
		map.put("email", uuid + "@gmail.com");
		map.put("gender", "male");
		map.put("status", "active");
		RestAssured.baseURI = "https://gorest.co.in/";
		RestAssured.basePath = "/public/v2/users";
		
	}
	
	@Test(priority=0)
	public void createUser() {
		
		Response response = RestAssured
		   .given()
		        .contentType("application/json")
		        .body(map)
		        .header("Authorization", "Bearer 0527a20aab1e58c512ec72886e185ded7af2499de5a7e3669115bab5353851cd")
		   .when()
		        .post()
		   .then()
		        .statusCode(201)
		        .log().all()
		        .contentType(ContentType.JSON).extract().response();
		
		JsonPath jsonPath = response.jsonPath();
		id = jsonPath.get("id");
		
	}
	
	@Test(priority=1)
	public void verifyUser() {
		RestAssured
	     .given()
		      .contentType("application/json")
              .header("Authorization", "Bearer 0527a20aab1e58c512ec72886e185ded7af2499de5a7e3669115bab5353851cd")
         .when()
		      .get("https://gorest.co.in/public/v2/users/" + id )
		  
		 .then()
		 .assertThat()
		 .statusCode(200)
		 .log().all();
	
	}
	@Test(priority=2)
	public void updateRespurce() {
		
		map.put("name", "Abdl_gh");
		map.put("email", uuid + "@gmail.com");
		map.put("gender", "male");
		map.put("status", "active");
		RestAssured.baseURI = "https://gorest.co.in/";
		RestAssured.basePath = "/public/v2/users/" + id;
		
		RestAssured
                
		 .given()
	         .contentType("application/json")
	         .body(map)
	         .header("Authorization", "Bearer 0527a20aab1e58c512ec72886e185ded7af2499de5a7e3669115bab5353851cd")
	    .when()
	         .put()
	     .then()
	         .statusCode(200)
	         .log().all();
	}
	
	@Test(priority=3)
	public void deleteUser() {
		
		RestAssured.baseURI = "https://gorest.co.in/";
		RestAssured.basePath = "/public/v2/users/" + id;
		
		RestAssured
        
		 .given()
	        .contentType("application/json")
	        .body(map)
	        .header("Authorization", "Bearer 0527a20aab1e58c512ec72886e185ded7af2499de5a7e3669115bab5353851cd")
	    .when()
	        .delete()
	    .then()
	         .statusCode(204);
		
	}

}
