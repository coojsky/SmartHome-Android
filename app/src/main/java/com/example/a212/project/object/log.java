package com.example.a212.project.object;

public class log {

    String Tech;
    String Switch;
    String Room;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    String UserID;
    String Date;
    public String getSwitch() {
        return Switch;
    }

    public void setSwitch(String Switch) {
        this.Switch = Switch;
    }



    public String getRoom() {
        return Room;
    }

    public void setRoom(String room) {
        Room = room;
    }

    public String getTech() {
        return Tech;
    }

    public void setTech(String tech) {
        Tech = tech;
    }



    public log(String Room, String Tech, String Switch,  String UserID, String Date){
        this.Room = Room;
        this.Tech = Tech;
        this.Switch = Switch;
        this.UserID = UserID;
        this.Date = Date;
    }


}
