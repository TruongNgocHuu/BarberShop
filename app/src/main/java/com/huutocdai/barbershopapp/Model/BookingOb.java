package com.huutocdai.barbershopapp.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BookingOb implements Serializable {
    private String Name;
    private String PhoneNumber;
    private String Date;
    private String Time;
    private String State;

    public BookingOb(String name, String phoneNumber, String date, String time, String state) {
        Name = name;
        PhoneNumber = phoneNumber;
        Date = date;
        Time = time;
        State = state;
    }

    public BookingOb(String name, String phoneNumber, String time) {
        Name = name;
        PhoneNumber = phoneNumber;
        Time = time;
    }

    public BookingOb(String time, String state) {
        Time = time;
        State = state;
    }

    public BookingOb(String time) {
        Time = time;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {

        State = state;
    }

    public BookingOb() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("PhoneNumber", this.getPhoneNumber());
        map.put("Date", this.getDate());
        map.put("Time", this.getTime());
        map.put("Name", this.getName());
        return map;
    }
}
