package edu.csumb.garc4464.otterairways;

import java.util.Objects;

/**
 * Created by anitagarcia on 5/11/16.
 */

public class Flight {
    private String flightNo;
    private String departure;
    private String arrival;
    private String time;
    private double price;
    private int capacity;

    public Flight(){
        flightNo = "UNKNOWN";
        departure = "UNKNOWN";
        arrival = "UNKNOWN";
        time = "UNKNOWN";
        price = 0.0;
        capacity = 0;
    }
    public Flight(String flightNo, String departure, String arrival, String time, double price, int capacity){
        this.flightNo = flightNo;
        this.departure = departure;
        this.arrival = arrival;
        this.time = time;
        this.price = price;
        this.capacity = capacity;
    }
    public String getFlightNo(){
        return flightNo;
    }
    public String getDeparture(){
        return departure;
    }
    public String getArrival(){
        return arrival;
    }
    public String getTime(){
        return time;
    }
    public double getPrice(){
        return price;
    }
    public int getCapacity(){
        return capacity;
    }
    public String toString(){
        return flightNo + " " + departure + " " + arrival + " " + time + " $" + price + " " + capacity + "\n";
    }
    public boolean equals(Object obj){
        if(obj instanceof Flight){
            Flight other = (Flight) obj;
            return other.getFlightNo().equals(this.flightNo);
        }
        return false;
    }
    public void setCapacity(int no){
        this.capacity = no;
    }
    /*public boolean equals(Object obj){
        if(obj instanceof Flight){
            Flight other = (Flight) obj;
            return other.departure.equals(this.departure) && other.arrival.equals(this.arrival)
        }
    }*/
}

