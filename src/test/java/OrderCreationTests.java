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
        // Берём первый существующий хеш ингредиента с тестового сервера
        String ingredientId = OrderSteps.fetchIngredientId();
        // Создание заказа с ингредиентами
        Response response = OrderSteps.createOrder(accessToken, new String[]{ingredientId});

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

        Assert.assertEquals(500, response.getStatusCode());
        // Проверяем тело ответа
        Assert.assertTrue(response.jsonPath().getBoolean("success"));
        Assert.assertEquals("Internal Server Error", response.jsonPath().getString("message"));
    }
}
