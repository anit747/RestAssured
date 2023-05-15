import org.testng.annotations.Test;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;


public class DynamicJson {
	public static void main(String[] args) {
		
	
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
	String response =	given().log().all().header("Content-Type","application/json").body(payload.addBookPayload())
		.when().post("/Library/Addbook.php").then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
	
	
	JsonPath js = new JsonPath(response);
	String id =js.get("ID");
	System.out.println(id);
	
		
	}
	}

