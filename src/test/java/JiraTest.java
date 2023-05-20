import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

public class JiraTest {

	public static void main(String[] args) {

		RestAssured.baseURI = "http://localhost:8080";
		// Login
		SessionFilter session = new SessionFilter();
		String response = given().relaxedHTTPSValidation().header("Content-Type", "application/json")
				.body("{ \"username\": \"anitrai744\", \"password\": \"Iamanitrai@747\" }").filter(session).log().all()
				.when().post("/rest/auth/1/session").then().log().all().statusCode(200).extract().response().asString();
		String expectedMessage = "HiIamAnit";
		// Add Comment
		String addCommentResponse = given().log().all().header("Content-Type", "application/json")
				.pathParam("id", "10024")
				.body("{\r\n" + "    \"body\": \"HiIamAnit\",\r\n" + "    \"visibility\": {\r\n"
						+ "        \"type\": \"role\",\r\n" + "        \"value\": \"Administrators\"\r\n" + "    }\r\n"
						+ "}")
				.filter(session).when().post("/rest/api/2/issue/{id}/comment").then().log().all().statusCode(201)
				.extract().response().asString();

		JsonPath js = new JsonPath(addCommentResponse);
		String commentId = js.getString("id");

		// Add Attachment

		given().header("X-Atlassian-Token", "no-check").header("Content-Type", "multipart/form-data")
				.pathParam("id", "10024").filter(session).log().all().multiPart("file", new File("TestFile.txt")).when()
				.post("/rest/api/2/issue/{id}/attachments").then().log().all().assertThat().statusCode(200);

		// Get Issue

		String issueDetails = given().filter(session).log().all().pathParam("id", "10024")
				.queryParam("fields", "comment").when().get("/rest/api/2/issue/{id}").then().log().all().assertThat()
				.statusCode(200).extract().response().asString();

		JsonPath js1 = new JsonPath(issueDetails);
		int commentCount = js1.getInt("fields.comment.comments.size()");
		for (int i = 0; i < commentCount; i++) {

			String commentIdIssue = js1.get("fields.comment.comments[" + i + "].id").toString();
			if (commentIdIssue.equalsIgnoreCase(commentId)) {

				String message = js1.get("fields.comment.comments[" + i + "].body").toString();
				System.out.println(message);
				Assert.assertEquals(message, expectedMessage);
			}
		}

	}

}
