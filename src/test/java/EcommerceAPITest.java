import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import Pojo.LoginRequest;
import Pojo.LoginResponse;
import Pojo.OrderDetail;
import Pojo.Orders;


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
	 	
	 	//Create Order
	 	
	 	
		RequestSpecification createOrderBasereq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
		setContentType(ContentType.JSON).addHeader("Authorization", token).build();
		
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setCountry("India");
		orderDetail.setProductOrderedId(productId);
		
		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		
		orderDetailList.add(orderDetail);
		Orders order = new Orders();
		order.setOrders(orderDetailList);
		

		RequestSpecification createOrderreq = 	given().log().all().spec(createOrderBasereq).body(order);
		
	String responseOrder =	createOrderreq.when().post("/api/ecom/order/create-order").then().log().all().extract().response().asString();
	
	
	
	// Delete product 
	
	
	RequestSpecification deleteOrderBasereq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token).build();
	 
	RequestSpecification deleteOrderReq	 = given().log().all().spec(deleteOrderBasereq).pathParam("productId",productId );
String deleteProductResponse = 	deleteOrderReq.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().extract().response().asString();

JsonPath js1 = new JsonPath (deleteProductResponse);
Assert.assertEquals("Product Deleted Successfully", js1.get("message"));

	}

}
