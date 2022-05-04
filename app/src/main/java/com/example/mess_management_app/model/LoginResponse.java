package com.example.mess_management_app.model;

public class LoginResponse {
    String message;
    String token;
    Data data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }



    public LoginResponse(String message, String token, Data data) {
        this.message = message;
        this.token = token;
        this.data = data;
    }
}
