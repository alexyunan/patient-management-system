import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author: Alexandros Giounan
 * @code @created: 7/23/2025
 */

public class PatientIntegrationTest {
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://192.168.1.8:4004";
    }

    @Test
    public void shouldReturnPatientsWithValidToken() {
        // 1. Arrange
        String loginPayload = """
                    {
                        "email": "testuser@test.com",
                        "password": "password123"
                    }
                """;
        // 2. Act
        // 3. Assert
        String token = RestAssured.given()
                .contentType("application/json")
                .body(loginPayload)  // Arrange
                .when() // Act
                .post("/auth/login")
                .then()// Assert
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("token");

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/patients")
                .then()
                .statusCode(200)
                .body("patients", notNullValue());
    }

    @Test
    public void shouldReturnPatientWithValidEmail() {

        String loginPayload = """
                    {
                        "email": "testuser@test.com",
                        "password": "password123"
                    }
                """;

        String token = RestAssured.given()
                .contentType("application/json")
                .body(loginPayload)  // Arrange
                .when() // Act
                .post("/auth/login")
                .then()// Assert
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("token");

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/patients/123e4567-e89b-12d3-a456-426614174000")
                .then()
                .statusCode(200)
                .body("email", equalTo("john.doe@example.com"));
    }

    // Test for creating a new patient
    @Test
    public void shouldCreateNewPatientWithValidToken() {
        // 1. Arrange
        String loginPayload = """
                    {
                        "email": "testuser@test.com",
                        "password": "password123"
                    }
                """;

        String token = RestAssured.given()
                .contentType("application/json")
                .body(loginPayload)  // Arrange
                .when() // Act
                .post("/auth/login")
                .then()// Assert
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("token");

        String newPatientPayload = """
                {
                    "name": "Jane Doe",
                    "email": "jane.doe@test.com",
                    "address": "123 Main St, Springfield",
                    "dateOfBirth": "1990-01-01",
                    "registeredDate": "2023-01-01"
                    }
                """;

        // 2. Act
        // 3. Assert
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(newPatientPayload)  // Arrange
                .when() // Act
                .post("/api/patients")
                .then()// Assert
                .log()
                .all()
                .statusCode(200);
    }
}
