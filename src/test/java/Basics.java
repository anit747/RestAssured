import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.payload;


public class Basics {
	
	public static void main(String[] args) throws IOException {
		
		//given - all input details
		//when- Submit the APIs
		//then - validate the response
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", " qaclick123").header("Content-Type","application/json").body(new String (Files.readAllBytes(Paths.get("C:/Users/Public/Documents/addPlaceJson.json")))).when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).log().all().body("scope", equalTo("APP")).header("Server","Apache/2.4.41 (Ubuntu)").extract().response().asString();
		
		JsonPath js = new JsonPath(response);
	String placeId=	js.getString("place_id");
	System.out.println(placeId);
	
	// update place
	String newAdress = "Summer Walk ";
	given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").body("{\r\n"
			+ "\"place_id\":\""+placeId+"\",\r\n"
			+ "\"address\":\""+newAdress+"\",\r\n"
			+ "\"key\":\"qaclick123\"\r\n"
			+ "}").when().put("/maps/api/place/update/json")
	.then().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated")).log().all();
	
	
	//get place 
	
	
String getPlace=	given().queryParam("key", "qaclick123").queryParam("place_id", placeId).log().all().when().get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		
JsonPath js1 = new JsonPath(getPlace);
String actualAddress = js1.getString("address");
System.out.println(actualAddress);
 Assert.assertEquals(actualAddress, newAdress);
	}
	
	
	
	

}
