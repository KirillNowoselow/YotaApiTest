import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import steps.Steps;

import static io.restassured.RestAssured.given;

public class BaseTest implements Steps {
    @BeforeMethod(description = "Настройки запроса")
    public void configureRestAssured() {
        RestAssured.baseURI = "http://localhost:8080/";
        RestAssured.requestSpecification = given()
              .contentType(ContentType.JSON);

    }
}