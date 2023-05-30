import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import Pojo.AddPlace;
import Pojo.Location;
import files.payload;

public class serializeTest {
	
	public static void main(String[] args) {
		
		AddPlace place = new AddPlace();
		place.setAccuracy(50);
		place.setAddress("eee");
		place.setLanguage("Hondi");
		place.setPhone_number("555");
		place.setWebsite("anit.com");
		place.setName("Anit");
		
		List <String> l = new ArrayList <String>();
		l.add("Show Park");
		l.add("Cloud");
		
		
		Location loc = new Location();
		loc.setLat(33.555);
		loc.setLng(88.555);
		
		place.setLocation(loc);
		
		
		
		
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
	String response =	given().queryParam("key","qaclick123").body(place).log().all()
		.when().post("/maps/api/place/add/json").then().assertThat().statusCode(200).extract().response().asString();
		
	
	System.out.println(response);
	}

}
