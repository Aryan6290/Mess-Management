package com.example.mess_management_app.model;

import java.util.ArrayList;

public class GetMealResponse {
    String message;
    ArrayList<DataX> meals;

    public GetMealResponse(String message, ArrayList<DataX> meals) {
        this.message = message;
        this.meals = meals;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<DataX> getMealList() {
        return meals;
    }

    public void setMealList(ArrayList<DataX> mealList) {
        this.meals = meals;
    }
}
