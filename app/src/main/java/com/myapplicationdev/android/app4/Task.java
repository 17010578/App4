package com.myapplicationdev.android.app4;

import java.io.Serializable;

public class Task implements Serializable {
    private int id;
    private String description;
    private String name;
    private int seconds;
    private String date;
    private String time;



    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public String getName() {
        return name;
    }

    public int getSeconds () {
        return seconds;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Task(int id, String name, String description, String date, String time) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
    }
    @Override
    public String toString() {
        return  id + " " + name + "\n" + description + "\n" + date + "\n" + time;

    }
}
