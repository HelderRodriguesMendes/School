package br.com.alura.school.registration;

public class UserRequest {
    private String username;

    public UserRequest(){}

    public UserRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
