import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

public class UserCreateTests {
    private String testUserEmail;
    private String password;
    private String name;

    @Test
    @DisplayName("Создание уникального пользователя")
    @Description("Тест на успешное создание уникального пользователя")
    public void testRegisterNewUser() {
        testUserEmail = "test_user_" + System.currentTimeMillis() + "@example.com";
        password = "password" + System.currentTimeMillis();
        name = "name" + System.currentTimeMillis();

        // Регистрация нового пользователя
        Response response = UserLoginSteps.registerUser(testUserEmail, password, name);

        // Проверяем код ответа
        Assert.assertEquals(200, response.getStatusCode());
        // Проверяем тело ответа
        Assert.assertTrue(response.jsonPath().getBoolean("success"));
        Assert.assertNotNull(response.jsonPath().getString("user.email"));
        Assert.assertEquals(name, response.jsonPath().getString("user.name"));

        UserLoginSteps.deleteUser(testUserEmail, password, name);

    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Description("Тест на ошибку при создании пользователя, который уже зарегистрирован")
    public void testRegisterExistingUser() {
        // Попытка зарегистрировать существующего пользователя
        Response response = UserLoginSteps.registerUser(UserData.EXISTING_USER_EMAIL, UserData.EXISTING_USER_PASSWORD, UserData.EXISTING_USER_NAME);

        // Проверяем код ответа
        Assert.assertEquals(403, response.getStatusCode());
        // Проверяем тело ответа
        Assert.assertFalse(response.jsonPath().getBoolean("success"));
        Assert.assertEquals("User already exists", response.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Создание пользователя с пропущенным обязательным полем")
    @Description("Тест на ошибку при создании пользователя с пропущенным обязательным полем")
    public void testRegisterUserMissingField() {
        // Попытка зарегистрировать пользователя с пропущенным обязательным полем
        Response response = UserLoginSteps.registerUser("test@example.com", "", "TestUser");

        // Проверяем код ответа
        Assert.assertEquals(403, response.getStatusCode());
        // Проверяем тело ответа
        Assert.assertFalse(response.jsonPath().getBoolean("success"));
        Assert.assertEquals("Email, password and name are required fields", response.jsonPath().getString("message"));
    }
}

