package RestAPITest;


import java.util.ArrayList;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.path.json.JsonPath;


public class AssurityTestAPI {
	
	private Response response;
	private JsonPath jsonpathevaluator;
	
	@BeforeTest
	public void Configuration()
	{
		// Declare the base URL to the RESTful web service
		RestAssured.baseURI = "https://api.tmsandbox.co.nz/v1/Categories/6327/Details.json?catalogue=false";
			
		//Get the RequestSpecification of the request that needs to be sent to the server. 
		//The server will hit the base URI we mentioned in the above step
		RequestSpecification httpRequest = RestAssured.given();
				
		// Use the RequestSPecification.get() method to directly make a Get Method call to the server.
		// Get method is use to retrieve some specific information from Server.
		response = httpRequest.get();
				
		//Get the JsonPath object instance from the Response interface	
		jsonpathevaluator = response.jsonPath();
				
	}
	
	
	@Test(priority = 0)
	//This Test will query the JsonPath object to get a String value of the node specified by JsonPath: "Name" 
	public void acceptanceCriteria1()
	{
		String name = jsonpathevaluator.getString("Name");
		System.out.println("Acceptance Criteria 1: Name =\"" + name + "\" received in the Response. ");
		
		// Validate the response
		Assert.assertEquals(name, "Carbon credits", "Expected Name value\"" + name + "\" received in the Response. ");
	}	 
		 
	
	
	
	@Test(priority = 1)
	//This test will query the JsonPath object to get a boolean value of the node specified by JsonPath: "CanRelist"
	public void acceptanceCriteria2()
	{
		
		boolean canRelist = jsonpathevaluator.get("CanRelist");
		System.out.println("Acceptance Criteria 2: CanRelist =\"" + canRelist + "\" received in the Response. ");
		
		// Validate the response
		Assert.assertEquals(canRelist, true, "Expected CanRelist value\"" + canRelist + "\" received in the Response. ");
	}
	
	
	
	
	@Test(priority = 2)
	//This test will Iterate the Promotions nested nodes to find the desired node (Name=Gallery) and (Description contains "2x larger image" text)
	public void acceptanceCriteria3()
	{
		//get the Promotion element list in an Array , to check the size and run the loop
		ArrayList<String> Promotionlist = jsonpathevaluator.get("Promotions");
		
		boolean AcceptCriteria3;
		for(int i=0; i<Promotionlist.size() ; i++)
		{
			String Test = "Promotions["+i+"]";
			
			//Use Map Interface to search elements on the basis of Key
			Map<String, String> company = response.jsonPath().getMap(Test);
			
			if (company.get("Name").equalsIgnoreCase("Gallery"))
			{
				String Desc = company.get("Description");
				
				if (Desc.contains("2x larger image"))
				{
					AcceptCriteria3 = true;
					System.out.println("Acceptance Criteria 3: The Promotion Element with Name =\"" + company.get("Name") + "\" has a Description that contains text \"2x larger image\"");
					Assert.assertEquals(AcceptCriteria3, true, "Expected Promotion Element with Name\"" + company.get("Name") + "\" has a Description that contains text \"2x larger image\"");
				}
			
			}
		
		}
		
		

		
	}
	

}
