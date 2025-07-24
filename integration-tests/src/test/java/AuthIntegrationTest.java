import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.notNullValue;

/**
 * @author: Alexandros Giounan
 * @code @created: 7/23/2025
 */

public class AuthIntegrationTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://192.168.1.8:4004";
    }

    @Test
    public void shouldReturnOKWithValidToken() {
        // 1. Arrange
        String loginPayload = """
                    {
                        "email": "testuser@test.com",
                        "password": "password123"
                    }
                """;
        // 2. Act
        // 3. Assert
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(loginPayload)  // Arrange
                .when() // Act
                .post("/auth/login")
                .then()// Assert
                .statusCode(200)
                .body("token", notNullValue())
                .extract().response();

        System.out.println("Generated Token: " + response.jsonPath().getString("token"));
    }


    @Test
    public void shouldReturnUnauthorizedOnInvalidLogin() {
        // 1. Arrange
        String loginPayload = """
                    {
                        "email": "invalid_user@test.com",
                        "password": "password12"
                    }
                """;
        // 2. Act
        // 3. Assert
        RestAssured.given()
                .contentType("application/json")
                .body(loginPayload)  // Arrange
                .when() // Act
                .post("/auth/login")
                .then()// Assert
                .statusCode(401);
    }
}
