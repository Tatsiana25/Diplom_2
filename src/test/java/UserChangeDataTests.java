import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

public class UserChangeDataTests {

    String newName = TestDataGenerator.generateRandomUsername();
    private String accessToken = UserLoginSteps.getAccessToken(UserData.CHANGE_USER_EMAIL, UserData.CHANGE_USER_PASSWORD);

    @Test
    @DisplayName("Изменение данных пользователя с авторизацией")
    @Description("Тест на успешное изменение данных пользователя с авторизацией")
    public void testUpdateUserProfileAuthorized() {
        // Обновление данных пользователя
        Response response = UserLoginSteps.updateUserProfile(accessToken, newName);

        // Проверяем код ответа
        Assert.assertEquals(200, response.getStatusCode());
        // Проверяем тело ответа
        Assert.assertTrue(response.jsonPath().getBoolean("success"));
        Assert.assertNotNull(response.jsonPath().getString("user.email"));
        Assert.assertEquals(newName, response.jsonPath().getString("user.name"));
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    @Description("Тест на ошибку при изменении данных пользователя без авторизации")
    public void testUpdateUserProfileUnauthorized() {
        // Обновление данных пользователя без авторизации
        Response response = UserLoginSteps.updateUserProfile("", "NewUsername");

        // Проверяем код ответа
        Assert.assertEquals(401, response.getStatusCode());
        // Проверяем тело ответа
        Assert.assertFalse(response.jsonPath().getBoolean("success"));
        Assert.assertEquals("You should be authorised", response.jsonPath().getString("message"));
    }
}
