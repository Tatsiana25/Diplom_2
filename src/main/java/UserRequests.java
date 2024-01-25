public class UserRequests {
    private String email;
    private String password;
    private String name;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static UserRequests createAndDeleteRequest(String email, String password, String name) {
        UserRequests request = new UserRequests();
        request.setEmail(email);
        request.setPassword(password);
        request.setName(name);
        return request;
    }

    public static UserRequests loginRequest(String email, String password) {
        UserRequests request = new UserRequests();
        request.setEmail(email);
        request.setPassword(password);
        return request;
    }

    public static UserRequests updateRequest(String newName) {
        UserRequests request = new UserRequests();
        request.setName(newName);
        return request;
    }
}
