package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static constants.UrlAddresses.MAIN_URL;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class AuthorizationCourierTest {
    private final CourierTestData courierTestData = new CourierTestData();
    String id = null;

    @Before
    public void setUp() {
        RestAssured.baseURI = MAIN_URL;
    }

    @Test
    @DisplayName("Успешный логин")
    @Description("Логин курьера в системе \n \n Курьер может авторизоваться; \n успешный запрос возвращает id.")
    public void loginCourier() {
        Courier courier = new Courier(courierTestData.getExistingLogin(), courierTestData.getExistingPassword());
        CourierMethods.createCourier(courier);
        id = CourierMethods.loginCourier(courier).then().extract().path("id").toString();
        Response response = CourierMethods.loginCourier(courier);
        response.then().assertThat().statusCode(200)
                .and()
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация без логина")
    @Description("Для авторизации курьера необходимо передать все обязательные поля. Здесь передается пустой логин курьера")
    public void loginCourierWithoutLogin() {
        Courier courier = new Courier("", courierTestData.getExistingPassword());
        Response response = CourierMethods.loginCourier(courier);
        response.then().assertThat().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));

    }

    @Test
    @DisplayName("Авторизация без пароля")
    @Description("Для авторизации курьера необходимо передать все обязательные поля. В данном случае передается пустой пароль курьера")
    public void loginCourierWithoutPassword() {
        Courier courier = new Courier(courierTestData.getExistingLogin(), "");
        Response response = CourierMethods.loginCourier(courier);
        response.then().assertThat().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация с неверным логином")
    @Description("Для авторизации курьера необходимо передать существующие креды. В данном случае передается неверный логин курьера")
    public void loginCourierWithNonExistLogin() {
        Courier courier = new Courier(courierTestData.getNonExistLogin(), courierTestData.getExistingPassword());
        Response response = CourierMethods.loginCourier(courier);
        response.then().assertThat().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация с неверным паролем")
    @Description("Для авторизации курьера необходимо передать существующие креды. В данном случае передается неверный пароль курьера")
    public void loginCourierWithNonExistPassword() {
        Courier courier = new Courier(courierTestData.getExistingLogin(), courierTestData.getNonExistPassword());
        Response response = CourierMethods.loginCourier(courier);
        response.then().assertThat().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void deleteCourier() {
        if (id != null) {
            CourierMethods.deleteCourier(id);
        }
    }
}
