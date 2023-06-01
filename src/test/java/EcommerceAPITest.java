import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

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
	}

}
