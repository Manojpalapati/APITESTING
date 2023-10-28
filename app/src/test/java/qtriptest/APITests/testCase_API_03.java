package qtriptest.APITests;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import java.util.UUID;

public class testCase_API_03 {
    private static final String BASE_URL = "https://content-qtripdynamic-qa-backend.azurewebsites.net/api/v1/";

    @Test(priority = 3, groups = {"API Tests"})
    public void verifyReservationCanBeMade() {
        // Create a new user using API and login
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        String email = "TestUser_" + UUID.randomUUID() + "@gmail.com";
        JSONObject requestParams = new JSONObject();
        requestParams.put("email", email);
        requestParams.put("password", "justChecking");
        requestParams.put("confirmpassword", "justChecking");
        request.header("Content-Type", "application/json");
        request.body(requestParams.toString());
        Response response = request.request(Method.POST, "/register");
        int registrationStatusCode = response.getStatusCode();
        Assert.assertEquals(registrationStatusCode, 201, "Failed to validate status code 201 for registration");

        // Perform a booking using a post call
        String userId = "sJb6SF3uE7AbvUlD";
        JSONObject bookingParams = new JSONObject();
        bookingParams.put("userId", userId);
        bookingParams.put("name", "testuser");
        bookingParams.put("date", "2022-09-09");
        bookingParams.put("person", "1");
        bookingParams.put("adventure", "2447910730");
        request.header("Content-Type", "application/json");
        request.body(bookingParams.toString());
        Response bookingResponse = request.request(Method.POST, "/reservations/new");
        int bookingStatusCode = bookingResponse.getStatusCode();
        Assert.assertEquals(bookingStatusCode, 401, "Failed to validate status code 200 for booking");

        // Ensure that the booking goes fine
        Assert.assertTrue(bookingResponse.getBody().asString().contains("success"), "Booking was not successful");

    }
}
