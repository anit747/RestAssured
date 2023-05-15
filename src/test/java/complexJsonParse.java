import org.testng.Assert;

import files.payload;
import io.restassured.path.json.JsonPath;

public class complexJsonParse {

	public static void main(String[] args) {
		
		JsonPath js = new JsonPath(payload.CoursePrices());
		
		//Print number of courses 
		
	int  count=	js.getInt("courses.size()");
	System.out.println(count);
	 
//Print Purchase Amount 
	
int purchaseAmnt =	js.getInt("dashboard.purchaseAmount");
System.out.println(purchaseAmnt);

//Print title of the first course


String courseName = js.getString("courses.title[0]");
System.out.println(courseName);



//Print All course titles and their respective Prices

for(int i =0;i<count;i++)
{
	String allCourses = js.get("courses.title["+i+"]");
	System.out.println(allCourses);
int prices =	js.getInt("courses.price["+i+"]");
System.out.println(prices);
	
}

//Print no of copies sold by RPA Course

for(int i =0;i<count;i++)
{
	String allCourses = js.get("courses.title["+i+"]");

if(allCourses.equalsIgnoreCase("RPA")) {
	
	int copies = js.getInt("courses.price["+i+"]");
	System.out.println(copies);
}


	
}
//Verify if Sum of all Course prices matches with Purchase Amount
 int sum =0;
 int amount = 0;
for(int i =0;i<count;i++) 
{
int prices =js.getInt("courses.price["+i+"]");
int copies = js.getInt("courses.copies["+i+"]");
 amount  =prices*copies;

sum = sum + amount ;
}System.out.println(sum);
int totalAmount = js.getInt("dashboard.purchaseAmount");
Assert.assertEquals(sum, totalAmount);



	}

}
