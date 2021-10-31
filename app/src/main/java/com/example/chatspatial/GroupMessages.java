package com.example.chatspatial;

public class GroupMessages {
    private String from, message,time, date, name,type;

    public GroupMessages(){

    }

    public GroupMessages(String from, String message, String time, String date, String name, String type) {
        this.from = from;
        this.message = message;
        this.time = time;
        this.date = date;
        this.name = name;
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
