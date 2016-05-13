package edu.csumb.garc4464.otterairways;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by anitagarcia on 5/11/16.
 */
public class Cancelation extends Transaction {
    private int reservationNumber;
    private String flightNo;
    private String departure;
    private String arrival;
    private Time deptTime;
    private int numOfTickets;

    public Cancelation(int type, String username, Date time, Reservation res){
        super(type, username, time);
        this.reservationNumber = res.getResNumber();
        this.flightNo = res.getFlightNo();
        this.departure = res.getDeparture();
        this.arrival = res.getArrival();
        this.numOfTickets = res.getNumOfTickets();
    }
    public String toString(){
        return super.type + " " + super.username + " " + reservationNumber
                + " " + flightNo + " " + departure + " " + arrival +
                numOfTickets  + super.currentTime.toString();
    }

}

