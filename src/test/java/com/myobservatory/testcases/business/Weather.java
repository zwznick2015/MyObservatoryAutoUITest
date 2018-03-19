package com.myobservatory.testcases.business;

/**
 * 每个weatherListItem 可封装成对象，具体怎么用，还没想好
 */
public class Weather {

    private int itemID;
    private Object iconOfWeather;
    private String date,dateOfWeek,temperature,humidity,wind,details;

    public int getItemID(){
        return itemID;
    }

    public Object getIconOfWeather() {
        return iconOfWeather;
    }

    public String getDate() {
        return date;
    }

    public String getDateOfWeek() {
        return dateOfWeek;
    }

    public String getDetails() {
        return details;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getWind() {
        return wind;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDateOfWeek(String dateOfWeek) {
        this.dateOfWeek = dateOfWeek;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setIconOfWeather(Object iconOfWeather) {
        this.iconOfWeather = iconOfWeather;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }
    }
