import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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


        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/patient-service/v1/patients")
                .then()
                .statusCode(200)
                .body("data.patients", Matchers.notNullValue());

    }


}
