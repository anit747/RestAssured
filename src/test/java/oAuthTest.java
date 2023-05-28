import static io.restassured.RestAssured.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import Pojo.API;
import Pojo.GetCourse;
import Pojo.WebAutomation;
import io.restassured.parsing.Parser;
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
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AbUR2VPe0vbeJB9V8c0wAAReT-X9oDJ--H2ddNcwvbovVSkDBnQL9kTMBPpA2sVtBFMw5Q&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
	String partialCode = url.split("code=")[1];
	String Code = partialCode.split("&scope")[0];
	System.out.println(Code);
	
		
		
		
		
	String getAccessToken =	given().urlEncodingEnabled(false).queryParams("code",Code).queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type","authorization_code").when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();
		
	JsonPath js = new JsonPath(getAccessToken);
	String accessToken = js.getString("access_token");
		
		
		
		GetCourse  gc = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON).
		when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		//System.out.println(response);
		
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		List<API> apiCourses = gc.getCourses().getApi();
		for(int i =0;i<apiCourses.size();i++) {
			
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
				
			System.out.println(apiCourses.get(i).getPrice());
			}
		}
		
		List<WebAutomation> webAutomation = gc.getCourses().getWebAutomation();
		for (int i =0;i<webAutomation.size();i++) {
	System.out.println(webAutomation.get(i).getCourseTitle());		
		}
		
	}

}
