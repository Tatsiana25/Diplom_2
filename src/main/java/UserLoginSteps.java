import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserLoginSteps {

    @Step("Шаг: Логин пользователя с параметрами - email: {0}, password: {1}")
    static Response loginUser(String email, String password) {
        RestAssured.baseURI = UserData.BASE_URL;
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}")
                .post();
    }

    @Step("Шаг: Логин пользователя с параметрами - email: {0}, password: {1}, name: {2}")
    static Response registerUser(String email, String password, String name) {
        RestAssured.baseURI = UserData.REGISTERED_URL;
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"" + email + "\",\"password\":\"" + password + "\",\"name\":\"" + name + "\"}")
                .post();
    }

    @Step("Шаг: Удаление пользователя с параметрами - email: {0}, password: {1}, name: {2}")
    static Response deleteUser(String email, String password, String name) {
        RestAssured.baseURI = UserData.DELETE_URL;
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"" + email + "\",\"password\":\"" + password + "\",\"name\":\"" + name + "\"}")
                .delete();
    }

    @Step("Шаг: Логин под существующим пользователем для получения accessToken")
    static String getAccessToken(String email, String password) {
        Response response = loginUser(email, password);
        String accessToken = response.jsonPath().getString("accessToken");
        return accessToken;
    }

    @Step("Шаг: Изменение данных пользователя с авторизацией - access token: {0}, new name: {1}")
    static Response updateUserProfile(String accessToken, String newName) {
        RestAssured.baseURI = UserData.AUTHORIZATION_URL;
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .body("{\"name\":\"" + newName + "\"}")
                .patch();
    }
}

