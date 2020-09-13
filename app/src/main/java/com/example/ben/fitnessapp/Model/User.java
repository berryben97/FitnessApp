package com.example.ben.fitnessapp.Model;

public class User {

    private String Fullname;
    private String Email;
    private String CurrentBmi;
    private String CalorieIntake;
//    private Float CurrentBMI;
    public User(){

    }

    public User(String fullname, String email, String currentBmi, String calorieintake) {
        Fullname = fullname;
        Email = email;
        CurrentBmi = currentBmi;
        CalorieIntake = calorieintake;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCurrentBmi() {
        return CurrentBmi;
    }

    public void setCurrentBmi(String currentBmi) {
        CurrentBmi = currentBmi;
    }

    public String getCalorieIntake() {
        return CalorieIntake;
    }

    public void setCalorieIntake(String calorieIntake) {
        CalorieIntake = calorieIntake;
    }
}

