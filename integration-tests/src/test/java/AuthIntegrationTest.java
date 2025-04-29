import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;



public class AuthIntegrationTest {
    @BeforeAll
    static void setUp(){
        RestAssured.baseURI = "http://localhost:4004";
    }

    @Test
    public void shouldReturnOKWithValidToken(){
        //1.Arrange
        //2.Act
        //3.Assert

        String loginPayLoad = """
                {
                    "email": "testuser@test.com",
                    "password": "password123"
                }
                
                """;
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(loginPayLoad)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("data.token", Matchers.notNullValue())
                .extract().response();

        System.out.println("Generated Token: " + response.jsonPath().getString("data.token"));

    }

    @Test
    public void shouldReturnUnauthorizedOnInvalidLogin(){
        //1.Arrange
        //2.Act
        //3.Assert

        String loginPayLoad = """
                {
                    "email": "invalid_user@test.com",
                    "password": "wrongPassword"
                }
                
                """;
       RestAssured.given()
                .contentType("application/json")
                .body(loginPayLoad)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(401);


    }
}
