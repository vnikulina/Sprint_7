package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static constants.UrlAddresses.MAIN_URL;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreatingCourierTest {
    private final CourierTestData courierTestData = new CourierTestData();
    String id = null;

    @Before
    public void setUp() {
        RestAssured.baseURI = MAIN_URL;
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Создание курьера со всеми заполненными полями")
    public void createCourier() {
        Courier courier = new Courier(courierTestData.getExistingLogin(), courierTestData.getExistingPassword(), courierTestData.getFirstName());
        Response response = CourierMethods.createCourier(courier);
        id = CourierMethods.loginCourier(courier)
                .then().extract()
               .path("id").toString();
        response.then().assertThat().statusCode(201)
                .and()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Создание курьера с указанием только имени и пароля")
    public void createCourierWithoutLogin() {
        Courier courier = new Courier("", courierTestData.getExistingPassword(), courierTestData.getFirstName());
        Response response = CourierMethods.createCourier(courier);
        response.then().assertThat().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Создание курьера с указанием только имени и логина")
    public void createCourierWithoutPassword() {
        Courier courier = new Courier(courierTestData.getExistingLogin(), "", courierTestData.getFirstName());
        Response response = CourierMethods.createCourier(courier);
        response.then().assertThat().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Создание курьера с указанием валидных данных, затем создание курьера с теми же данными")
    public void createTwoSameCouriers() {
        Courier courier = new Courier(courierTestData.getExistingLogin(), courierTestData.getExistingPassword(), courierTestData.getFirstName());
        CourierMethods.createCourier(courier);
        Response response = CourierMethods.createCourier(courier);
        id = CourierMethods.loginCourier(courier).then().extract().path("id").toString();
        response.then().assertThat().statusCode(409)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
    @After
    public void deleteCourier() {
        if (id != null) {
            CourierMethods.deleteCourier(id);
        }
    }
}
