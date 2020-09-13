package com.example.ben.fitnessapp.Model;

public class WorkoutList {

    private String name;
    private String rep;
    private String gif;

    public WorkoutList(){}

    public WorkoutList(String name, String rep, String gif) {
        this.name = name;
        this.rep = rep;
        this.gif = gif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }

    public String getGif() {
        return gif;
    }

    public void setGif(String gif) {
        this.gif = gif;
    }
}
