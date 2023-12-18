package order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static constants.UrlAddresses.ORDER;
import static io.restassured.RestAssured.given;

public class OrderMethods extends User {
    @Step("Создание заказа")
    public static Response createOrder(Order order) {
        return given()
                .header("Content-Type", "application/json")
                .and()
                .body(order)
                .when()
                .post(ORDER);
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList() {
        return given()
                .spec(getSpec())
                .when()
                .get(ORDER)
                .then();
    }

    @Step("Отмена заказа")
    public static void cancelOrder(String orderId) {
        given()
                .delete(ORDER + "{orderId}", orderId);
    }
}
