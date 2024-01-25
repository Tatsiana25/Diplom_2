import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class OrderSteps {
    @Step("Шаг: Создание заказа - access token: {0}, ingredients: {1}")
    static Response createOrder(String accessToken, String[] ingredients) {
        RestAssured.baseURI = UserData.ORDERS_URL;
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .body("{\"ingredients\":" + toJsonArray(ingredients) + "}")
                .post();
    }

    private static String toJsonArray(String[] elements) {
        StringBuilder jsonArray = new StringBuilder("[");
        for (int i = 0; i < elements.length; i++) {
            jsonArray.append("\"").append(elements[i]).append("\"");
            if (i < elements.length - 1) {
                jsonArray.append(",");
            }
        }
        jsonArray.append("]");
        return jsonArray.toString();
    }

    @Step("Шаг: Получение заказов пользователя с токеном - access token: {0}")
    static Response getUserOrders(String accessToken) {
        RestAssured.baseURI = UserData.ORDERS_URL;
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .get();
    }
    @Step("Шаг: Получение данных об ингредиентах и извлечение значения _id ")
    public static String fetchIngredientId() {
        RestAssured.baseURI = UserData.INFORMATION_INGREDIENTS_URL;
        Response response = RestAssured.get();

        // Извлечение значения _id из первого элемента массива data
        String ingredientId = response.jsonPath().getString("data[0]._id");

        return ingredientId;
    }
}
