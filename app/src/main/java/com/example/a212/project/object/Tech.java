package com.example.a212.project.object;


public class Tech {
    public String getSwitch() {
        return Switch;
    }
    public String ONSwitch(){
        Switch = "OFF";
        return Switch;
    }
    public String OFFSwitch(){
        Switch = "ON";
        return Switch;
    }
    public void setSwitch(String Switch) {
        this.Switch = Switch;
    }

    String Switch;
    String Room;

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

    String Tech;

    public Tech(String Room, String Tech, String Switch){
        this.Room = Room;
        this.Tech = Tech;
        this.Switch = Switch;
    }


}
