import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

import java.io.File;

import Pojo.LoginRequest;
import Pojo.LoginResponse;


public class EcommerceAPITest {
	
	public static void main(String[] args) {
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
		setContentType(ContentType.JSON).build();
		
		LoginRequest login = new LoginRequest();
		login.setUserEmail("anitrai747@gmail.com");
		login.setUserPassword("Hello@123");
		
		
		RequestSpecification reqLogin =	given().log().all().spec(req) .body(login);
			LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login").then().log().all().extract()
					.response().as(LoginResponse.class);
			
			System.out.println(loginResponse.getToken());
			System.out.println(loginResponse.getUserId());
			String token = loginResponse.getToken();
			String userId = loginResponse.getUserId();
			
			
			// Add the product 
			
			
			
			RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token).build();
			
	 		RequestSpecification	addProduct =	given().log().all().spec(addProductBaseReq).param("productName", "qwerty")
			.param("productAddedBy", userId).param("productCategory", "fashion").param("productSubCategory", "shirts").param("productPrice", "11500")
			.param("productDescription", "Addias Originals").param("productFor", "women")
			.multiPart("productImage",new File ("C:\\Users\\anitr\\Pictures\\Screenshots\\Screenshot 2023-05-21 114406.png")) ;
	 		
	 		
	 	String addProductResponse =	addProduct.when().post("/api/ecom/product/add-product").
	 		then().log().all().extract().response().asString  ();
	 	
	 	JsonPath js = new JsonPath (addProductResponse);
	 	String productId = js.getString("productId");
			
			
	}

}
