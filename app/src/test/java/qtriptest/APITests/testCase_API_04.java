package qtriptest.APITests;

import io.restassured.path.json.JsonPath;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import java.util.UUID;

public class testCase_API_04 {

    @Test(priority = 4, groups = {"API Tests"})
    public void testCase04() {
        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net/api/v1/";
        RequestSpecification httpRequest = RestAssured.given();
        String email = "TestUser_" + UUID.randomUUID() + "@gmail.com";

        // Step 1: Register a new user
        JSONObject reqParams1 = new JSONObject();
        reqParams1.put("email", email);
        reqParams1.put("password", "justChecking");
        reqParams1.put("confirmpassword", "justChecking");
        httpRequest.header("Content-Type", "application/json");
        httpRequest.body(reqParams1.toString());
        Response response1 = httpRequest.request(Method.POST, "/register");
        int registrationStatusCode1 = response1.getStatusCode();
        Assert.assertEquals(registrationStatusCode1, 201, "Failed to validate status code 201 for registration");

        // Step 2: Attempt to register a new user with the same email ID
        JSONObject reqParams2 = new JSONObject();
        reqParams2.put("email", email);
        reqParams2.put("password", "somePassword");
        reqParams2.put("confirmpassword", "somePassword");
        httpRequest.header("Content-Type", "application/json");
        httpRequest.body(reqParams2.toString());
        Response response2 = httpRequest.request(Method.POST, "/register");
        int registrationStatusCode2 = response2.getStatusCode();
        Assert.assertEquals(registrationStatusCode2, 400, "Second registration didn't fail with status code 400");
        
        JsonPath jsonPath = new JsonPath(response2.asString());
        Assert.assertEquals(jsonPath.getString("message"), "Email already exists", "Error message 'Email already exists' not found");
    }
}
