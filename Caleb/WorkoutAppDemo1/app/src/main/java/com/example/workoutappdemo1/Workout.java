package com.example.workoutappdemo1;

class Workout {

    private String wod;
    private String title;

    public Workout(String wod, String title) {
        this.wod = wod;
        this.title = title;
    }

    public Workout() {
    }

    public String getWod() {
        return wod;
    }

    public void setWod(String wod) {
        this.wod = wod;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
