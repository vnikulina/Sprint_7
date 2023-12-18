package order;

import java.util.HashMap;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static constants.UrlAddresses.MAIN_URL;
import static org.junit.Assert.*;

public class GetOrderListTest {
    private OrderMethods orderMethod;

    @Before
    public void setUp() {
        RestAssured.baseURI = MAIN_URL;
        orderMethod = new OrderMethods();
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка, что в тело ответа возвращается непустой список заказов")
    public void getOrderList() {
        ValidatableResponse responseCreate = orderMethod.getOrderList();
        int actualStatusCodeCreate = responseCreate.extract().statusCode();
        List<HashMap> orderBody = responseCreate.extract().path("orders");
        assertEquals(200, actualStatusCodeCreate);
        assertNotNull(orderBody);
        assertTrue(orderBody.get(1).get("id").toString().matches("[\\d]+"));
    }
}
