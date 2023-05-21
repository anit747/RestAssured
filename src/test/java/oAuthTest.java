import static io.restassured.RestAssured.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.restassured.path.json.JsonPath;
public class oAuthTest {
	
	
	public static WebDriver driver;
	public static void main(String[] args) throws InterruptedException {
		
		
//		driver = new EdgeDriver();
//		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
//		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("anitrai741@gmail.com");
//		driver.findElement(By.xpath("//input[@type='email']")).sendKeys(Keys.ENTER);
//		Thread.sleep(1000);
//		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Iamanitrai@747");
//		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(Keys.ENTER);
//	String url =	driver.getCurrentUrl();
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AbUR2VOL9NjwCUh28wMc6Fxj3oqwnNfGC2WJOw0A0Q1zVy74vnsdf32rs_cM53t1Hv_KMA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent";
	String partialCode = url.split("code=")[1];
	String Code = partialCode.split("&scope")[0];
	System.out.println(Code);
	
		
		
		
		
	String getAccessToken =	given().urlEncodingEnabled(false).queryParams("code",Code).queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type","authorization_code").when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();
		
	JsonPath js = new JsonPath(getAccessToken);
	String accessToken = js.getString("access_token");
		
		
		
		String response = given().queryParam("access_token", accessToken).
		when().get("https://rahulshettyacademy.com/getCourse.php").asString();
		System.out.println(response);
		
	}

}
