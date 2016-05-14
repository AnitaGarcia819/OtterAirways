package edu.csumb.garc4464.otterairways;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by anitagarcia on 5/11/16.
 */

public class ReserveSeat extends Transaction{
    private String flightNo;
    private String departure;
    private String arrival;
    private int numOfTickets;
    double totalAmount;

    public ReserveSeat(){
        this.flightNo = "UNKNOWN";
        this.departure = "UNKNOWN";
        this.arrival = "UNKNOWN";
        this.numOfTickets = 0;
        this.totalAmount = 0.0;
    }
    public ReserveSeat(int type, String username, Date time, Reservation res){
        super(type, username, time);
        this.flightNo = res.getFlightNo().toLowerCase();
        this.departure = res.getDeparture().toLowerCase();
        this.arrival = res.getArrival().toLowerCase();
        this.numOfTickets = res.getNumOfTickets();
        this.totalAmount = res.getTotalCost();
    }
    public String toString(){
        return super.type + " " + super.username
                + " " + flightNo + " " + departure + " " + arrival + " " +
                numOfTickets + " " + totalAmount + " " + super.currentTime.toString();
    }
}

