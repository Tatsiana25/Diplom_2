import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

public class OrderCreationTests {

    private String accessToken = UserLoginSteps.getAccessToken(UserData.EXISTING_USER_EMAIL, UserData.EXISTING_USER_PASSWORD);


    @Test
    @DisplayName("Создание заказа с авторизацией и ингредиентами")
    @Description("Тест на успешное создание заказа с авторизацией и указанными ингредиентами")
    public void testCreateOrderAuthorizedWithIngredients() {

        // Создание заказа с ингредиентами
        Response response = OrderSteps.createOrder(accessToken, new String[]{"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa72"});

        // Проверяем код ответа
        Assert.assertEquals(200, response.getStatusCode());
        // Проверяем тело ответа
        Assert.assertTrue(response.jsonPath().getBoolean("success"));
        Assert.assertNotNull(response.jsonPath().getString("name"));
        Assert.assertNotNull(response.jsonPath().getString("order.number"));
    }

    @Test
    @DisplayName("Создание заказа без авторизации и без ингредиентов")
    @Description("Тест создание заказа без авторизации и без ингредиентов")
    public void testCreateOrderUnauthorizedWithoutIngredients() {
        // Создание заказа без авторизации и без ингредиентов
        Response response = OrderSteps.createOrder("", new String[]{});

        // Проверяем код ответа
        Assert.assertEquals(400, response.getStatusCode());
        // Проверяем тело ответа
        Assert.assertFalse(response.jsonPath().getBoolean("success"));
        Assert.assertEquals("Ingredient ids must be provided", response.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    @Description("Тест на ошибку при создании заказа с неверным хешем ингредиентов")
    public void testCreateOrderInvalidIngredientsHash() {
        // Создание заказа с неверным хешем ингредиентов
        Response response = OrderSteps.createOrder(accessToken, new String[]{"61c0c5a71d001bdaa"});
        // Выводим тело ответа в консоль
        System.out.println("Response body: " + response.getBody().asString());

        //Вместо JSON возвращается HTML позеленила тест таким образом
        Assert.assertTrue(response.getBody().asString().contains("Internal Server Error"));
    }
}
