import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PatientIntegrationTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:4004";
    }

    @Test
    public void shouldReturnPatientsValidToken() {
        //1.Arrange
        //2.Act
        //3.Assert

        String token = getToken();


        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/patient-service/v1/patients")
                .then()
                .statusCode(200)
                .body("data.patients", Matchers.notNullValue());

    }


    @Test
    public void shouldReturn429AfterLimitExceed() throws InterruptedException {
        String token = getToken();
        int total = 10;
        int tooManyRequests = 0;

        for (int i = 0; i <= total; i++) {
            Response response = RestAssured.given()
                    .header("Authorization", "Bearer " + token)
                    .when()
                    .get("/api/patient-service/v1/patients");

            System.out.printf("Request %d -> Status: %d%n", i,
                    response.getStatusCode());

            if (response.getStatusCode() == 429) {
                tooManyRequests++;
            }
            Thread.sleep(100); // Sleep for 100 milliseconds between requests
        }

        assertTrue(tooManyRequests >= 1,
                "Expected at least 1 request to be rate limited (429)");

    }

    private static String getToken() {
        String loginPayLoad = """
                {
                    "email": "testuser@test.com",
                    "password": "password123"
                }
                
                """;
        String token = RestAssured.given()
                .contentType("application/json")
                .body(loginPayLoad)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("data.token");
        return token;
    }

}
