package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import models.RequestModel.*;
import models.ResponseModel.*;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class YotaApiSteps {
    @Step("Авторизация")
    public LoginResponse login(String login, String password) {
        return given()
            .body(new LoginRequest(login, password))
            .when()
            .post("login")
            .then()
                .statusCode(200).log().all()
                .extract().response().body().as(LoginResponse.class);
    }

    @Step("Получение списка свободных номеров")
    public PhonesResponse getEmptyPhone (String authToken) {
        return given()
                .header("authToken", authToken)
                .when()
                .get("simcards/getEmptyPhone")
                .then()
                .statusCode(200).log().all()
                .extract().response().body().as(PhonesResponse.class);
    }
    @Step("Добавление клиента")
    public CustomerIdResponse postCustomer(String authToken, String name, Long phone, AdditionalParametersRequest additionalParameters) {
         return given()
                .header("authToken", authToken)
                .body(new CustomerRequest(name, phone, additionalParameters))
                .when()
                .post("customer/postCustomer")
                .then()
                .statusCode(200).log().all()
                .extract().response().body().as(CustomerIdResponse.class);
    }


    @Step("Проверка данных клиента")
    public void getCustomerById (String authToken, String customerId, String statusCustomer, AdditionalParametersRequest additionalParameters) {
        CustomerResponse customerResponse = given()
                .header("authToken", authToken)
                .when()
                .get("customer/getCustomerById?customerId=" + customerId)
                .then()
                .statusCode(200).log().all()
                .extract().response().body().as(CustomerResponse.class);

        Assert.assertEquals(statusCustomer, customerResponse.getReturnCustomer().getStatus());

        Assert.assertEquals(customerResponse.getReturnCustomer().additionalParameters, additionalParameters);

        String passport = customerResponse.getReturnCustomer().pd;
        Integer expectedCountDigitPd = 10;
        Integer actualCountDigitPd = 0;
        for (int i = 0; i < passport.length(); i++) {
            if (Character.isDigit(passport.charAt(i))) {
                actualCountDigitPd++;
            }
        }
        Assert.assertEquals(expectedCountDigitPd, actualCountDigitPd);
    }
    @Step("Проверка, того что клиент сохранился в старой системе")
    public void findByPhoneNumber(String xmlBody) {
        given()
                .body(xmlBody).contentType(ContentType.XML)
                .when()
                .post("customer/findByPhoneNumber")
                .then()
                .statusCode(200).log().all();
    }

    @Step("Изменение статуса клиента")
    public void changeCustomerStatus( String authToken, String customerId, String statusCustomer, Integer status) {
        given()
                .header("authToken", authToken)
                .body(new StatusRequest(statusCustomer))
                .when()
                .post("customer/"+customerId+"/changeCustomerStatus")
                .then()
                .statusCode(status).log().all();
    }

}