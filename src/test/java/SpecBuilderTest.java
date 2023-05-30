import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

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

public class SpecBuilderTest {
	
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
		
		
	RequestSpecification req =	new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
		.setContentType(ContentType.JSON).build();
	ResponseSpecification resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
	RequestSpecification res =	given().spec(req).body(place).log().all();
		
			
	String response1 =		res.when().post("/maps/api/place/add/json").then().spec(resspec).extract().response().asString();
		
	
	System.out.println(response1);
	
	}

}
