package qtriptest.APITests;
import io.restassured.path.json.JsonPath;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import qtriptest.DP;
import io.restassured.RestAssured;
import java.util.UUID;


public class testCase_API_01 {
    
    @Test(priority = 1, groups = {"API Tests"})
    public void TestCase01(){
        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net/api/v1/";
        RequestSpecification httprequest = RestAssured.given();
        String email = "TestUser_" + UUID.randomUUID() + "@gmail.com";
        JSONObject reqParams1 = new JSONObject(); //creating a json object with the content we want to send
        reqParams1.put("email", email);
        reqParams1.put("password", "justChecking");
        reqParams1.put("confirmpassword", "justChecking");
        httprequest.header("Content-Type", "application/json"); //setting up the header for making a HTTP request
        httprequest.body(reqParams1.toString()); //passing the params as string
        System.out.println(httprequest.toString());
        Response response = httprequest.request(Method.POST, "/register"); //saving the response after doing the HTTP request
        int registrationStatusCode = response.getStatusCode();
        Assert.assertEquals(registrationStatusCode, 201, "Failed to validate status code 201 for registration");

        // Login API Testing
        RequestSpecification httprequest1 = RestAssured.given();
        JSONObject reqParams2 = new JSONObject();
        reqParams2.put("email", email);
        reqParams2.put("password", "justChecking");
        httprequest1.header("Content-Type", "application/json");
        httprequest1.body(reqParams2.toString());
        System.out.println(httprequest1.toString());
        Response response1 = httprequest1.request(Method.POST, "/login");
        String body = response1.getBody().asString();
        
        int loginStatusCode = response1.getStatusCode();
        Assert.assertEquals(loginStatusCode, 201);
        JsonPath jsonPath = new JsonPath(response1.asString());
        Assert.assertTrue(jsonPath.getBoolean("success"), "Failed to validate that login is successful");
        Assert.assertEquals(body.contains("token"), true ,"Failed to validate that token is returned in login");
        Assert.assertEquals(body.contains("id"), true ,"Failed to validate that userId is returned in login");
        Assert.assertEquals(body.contains("success"), true ,"Failed to validate that login is successful");
    }

}
