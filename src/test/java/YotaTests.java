import lombok.SneakyThrows;
import models.RequestModel.*;

import org.testng.annotations.Test;


public class YotaTests extends BaseTest{

    @SneakyThrows
    @Test(testName = "Тест активации клиента по шагам из бизнес-сценария", description = "Тест активации клиента по шагам из бизнес-сценария", priority = 3)
    public void checkGetCustomerById() {
        AdditionalParametersRequest additionalParameters = new AdditionalParametersRequest("string");
        String authToken = YOTA_API_STEPS.login("admin", "password").getToken(); //Авторизация и получение authToken
        Long phone = YOTA_API_STEPS.getEmptyPhone(authToken).getPhones().get(0).getPhone(); //Получение первого свободного номера
        String customerId = YOTA_API_STEPS.postCustomer(authToken,"Кирилл", phone, additionalParameters).getId(); //создание нового клиента
        Thread.sleep(120000);
        YOTA_API_STEPS.getCustomerById(authToken, customerId, "ACTIVE",additionalParameters); //Проверка корректности данных клиента спустя 2 минуты

        String xmlBody = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ns3:Envelope xmlns:ns2=\"soap\" xmlns:ns3=\"http://schemas.xmlsoap.org/soap/envelope\">\n" +
                "    <ns2:Header>\n" +
                "        <authToken>"+authToken+"</authToken>\n" +
                "    </ns2:Header>\n" +
                "    <ns2:Body>\n" +
                "        <phoneNumber>"+phone+"</phoneNumber>\n" +
                "    </ns2:Body>\n" +
                "</ns3:Envelope>\n";
        YOTA_API_STEPS.findByPhoneNumber(xmlBody); //Проверка, того что клиент сохранился в старой системе
    }

    @Test(testName = "Тест изменения статуса админом", description = "Тест изменения статуса админом", priority = 1)
    public void checkChangeCustomerStatusByAdmin() {
        AdditionalParametersRequest additionalParameters = new AdditionalParametersRequest("string");
        String authToken = YOTA_API_STEPS.login("admin", "password").getToken(); //Авторизация и получение authToken
        Long phone = YOTA_API_STEPS.getEmptyPhone(authToken).getPhones().get(0).getPhone(); //Получение первого свободного номера
        String customerId = YOTA_API_STEPS.postCustomer(authToken,"Тихон", phone, additionalParameters).getId(); //Создание нового клиента
        YOTA_API_STEPS.changeCustomerStatus(authToken, customerId, "ACTIVE", 200); //Изменение статуса админом
        YOTA_API_STEPS.getCustomerById(authToken, customerId,"ACTIVE", additionalParameters); //Проверка корректности данных клиента после изменения статуса в ручную
    }

    @Test(testName = "Тест изменения статуса обычным пользователем", description = "Тест изменения статуса обычным пользователем", priority = 2)
    public void checkChangeCustomerStatusByUser() {
        AdditionalParametersRequest additionalParameters = new AdditionalParametersRequest("string");
        String authToken = YOTA_API_STEPS.login("user", "password").getToken(); //Авторизация и получение authToken
        Long phone = YOTA_API_STEPS.getEmptyPhone(authToken).getPhones().get(0).getPhone(); //Получение первого свободного номера
        String customerId = YOTA_API_STEPS.postCustomer(authToken, "Даниил", phone, additionalParameters).getId(); //Создание нового клиента
        YOTA_API_STEPS.changeCustomerStatus(authToken, customerId, "ACTIVE", 401); //Попытка изменения статуса обычным пользователем
    }
}
