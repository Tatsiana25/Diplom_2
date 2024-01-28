import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

public class UserOrdersTest {

    private String accessToken = UserLoginSteps.getAccessToken(UserData.EXISTING_USER_EMAIL, UserData.EXISTING_USER_PASSWORD);

    @Test
    @DisplayName("Получение заказов авторизованного пользователя")
    @Description("Тест на успешное получение заказов авторизованного пользователя")
    public void testGetUserOrdersAuthorized() {
        // Получение заказов авторизованного пользователя
        Response response = OrderSteps.getUserOrders(accessToken);

        // Проверяем код ответа
        Assert.assertEquals(200, response.getStatusCode());
        // Проверяем тело ответа
        Assert.assertTrue(response.jsonPath().getBoolean("success"));
        Assert.assertNotNull(response.jsonPath().getList("orders"));
        Assert.assertNotNull(response.jsonPath().get("total"));
        Assert.assertNotNull(response.jsonPath().get("totalToday"));
    }

    @Test
    @DisplayName("Получение заказов неавторизованного пользователя")
    @Description("Тест на ошибку при получении заказов неавторизованного пользователя")
    public void testGetUserOrdersUnauthorized() {
        // Получение заказов неавторизованного пользователя
        Response response = OrderSteps.getUserOrders("");

        // Проверяем код ответа
        Assert.assertEquals(401, response.getStatusCode());
        // Проверяем тело ответа
        Assert.assertFalse(response.jsonPath().getBoolean("success"));
        Assert.assertEquals("You should be authorised", response.jsonPath().getString("message"));
    }
}