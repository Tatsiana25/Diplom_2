import com.github.javafaker.Faker;

public class TestDataGenerator {

    static Faker faker = new Faker();

    public static String generateRandomUsername() {
        return faker.name().username();
    }
}
