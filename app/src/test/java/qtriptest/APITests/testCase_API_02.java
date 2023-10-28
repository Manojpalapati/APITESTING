package qtriptest.APITests;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import qtriptest.DP;
import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;

public class testCase_API_02 {

    private static final String BASE_URL = "https://content-qtripdynamic-qa-backend.azurewebsites.net/api/v1";
    private static final String SEARCH_CITY_ENDPOINT = "/searchCity";

    @Test(priority = 2, groups = {"API Tests"})
    public void testCitySearchResultCount() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.queryParam("q", "beng");
        Response response = httpRequest.get("/cities");
        System.out.println("Response body: " + response.getBody().asString());
        int statusCode = response.getStatusCode();
        System.out.println("statusCode = " + statusCode);
        Assert.assertEquals(statusCode, 200, "Failed to validate status code 200 for search");

        JsonPath jsonPath = response.jsonPath();
        int size = jsonPath.getInt("size()");
        Assert.assertEquals(size, 1, "Array length not equal to 1");

        String description = jsonPath.getList("description").get(0).toString();
        Assert.assertTrue(description.contains("100+ Places"), "Description does not contain '100+ Places'");
    }
}
