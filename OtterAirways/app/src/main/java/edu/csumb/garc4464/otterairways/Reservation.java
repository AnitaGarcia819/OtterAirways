package edu.csumb.garc4464.otterairways;

/**
 * Created by anitagarcia on 5/11/16.
 */

public class Reservation {
    public int resNumber;
    public String departure;
    public String arrival;
    public Time depTime;
    public int noOfTickets;
    public String flightNo;
    public double totalCost;

    public Reservation(){
        this.resNumber = 0;
        this.departure = departure;
        this.arrival = arrival;
        this.depTime = new Time();
        this.noOfTickets = noOfTickets;
        this.flightNo = flightNo;
    }
    public Reservation(int resNumber, String departure, String arrival, Time time,int noOfTickets, String flightNo){
        this.resNumber = resNumber;
        this.departure = departure;
        this.arrival = arrival;
        this.depTime = time;
        this.noOfTickets = noOfTickets;
        this.flightNo = flightNo;
    }
    public int getResNumber(){
        return resNumber;
    }
    public String getFlightNo(){
        return flightNo;
    }
    public int getNumOfTickets(){
        return noOfTickets;
    }
    public double getTotalCost(){
        return totalCost;
    }
    public String getDeparture(){
        return departure;
    }
    public String getArrival(){
        return arrival;
    }
    public Time getTime(){
        return depTime;
    }
    public String toString(){
        return resNumber + " " + flightNo + " " + departure + " " + arrival + " " + depTime + " " + noOfTickets;
    }
}
