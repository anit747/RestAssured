import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;


public class DynamicJson {
	//public static void main(String[] args) {
		@Test(dataProvider = "Books")
		
		public static void libraryApi(String isbn, String aisle) {
	
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
	String response =	given().log().all().header("Content-Type","application/json").body(payload.addBookPayload(isbn,aisle))
		.when().post("/Library/Addbook.php").then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
	
	
	JsonPath js = new JsonPath(response);
	String id =js.get("ID");
	System.out.println(id);
	
		
	}
	//Parametrization	
		@DataProvider(name ="Books")
		
		public Object[][] getData() {
			
			return new Object[] [] {{"ddfg","2678"},{"eer","344"}};
		}
}

