import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

public class UserLoginTests {

    @Test
    @DisplayName("Логин под существующим пользователем")
    @Description("Тест на успешный логин под существующим пользователем")
    public void testLoginExistingUser() {

        // Логин пользователя
        Response response = UserLoginSteps.loginUser(UserData.EXISTING_USER_EMAIL, UserData.EXISTING_USER_PASSWORD);

        // Проверяем код ответа
        Assert.assertEquals(200, response.getStatusCode());
        // Проверяем тело ответа
        Assert.assertTrue(response.jsonPath().getBoolean("success"));
        Assert.assertNotNull(response.jsonPath().getString("accessToken"));
        Assert.assertNotNull(response.jsonPath().getString("refreshToken"));
        Assert.assertNotNull(response.jsonPath().getString("user.email"));
        Assert.assertEquals(UserData.EXISTING_USER_NAME, response.jsonPath().getString("user.name"));
    }

    @Test
    @DisplayName("логин с неверным логином и паролем")
    @Description("Тест на успешный логин под существующим пользователем")
    public void testLoginNotExistingUser() {

        // Логин пользователя
        Response response = UserLoginSteps.loginUser("vdf4", "4343");

        // Проверяем код ответа
        Assert.assertEquals(401, response.getStatusCode());
        // Проверяем тело ответа
        Assert.assertFalse(response.jsonPath().getBoolean("success"));
        Assert.assertEquals("email or password are incorrect", response.jsonPath().getString("message"));
    }
}
