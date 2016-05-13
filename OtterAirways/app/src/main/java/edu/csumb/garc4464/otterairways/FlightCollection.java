package edu.csumb.garc4464.otterairways;

/**
 * Created by anitagarcia on 5/11/16.
 */
import java.util.ArrayList;


public class FlightCollection {
    private ArrayList<Flight> flights;
    private static FlightCollection uniqueFlightCollection;

    private FlightCollection(){
        flights = new ArrayList<Flight>();
    }
    public static FlightCollection getInstance(){
        if(uniqueFlightCollection == null){
            uniqueFlightCollection = new FlightCollection();
        }
        return uniqueFlightCollection;
    }
    public ArrayList<Flight> getAvailableFlights(String departure, String arrival, int no){
        ArrayList<Flight> results = new ArrayList<Flight>();
        for(int i = 0; i < flights.size(); i++){
            if(flights.get(i).getDeparture().equals(departure)
                    && flights.get(i).getDeparture().equals(arrival)){
                results.add(flights.get(i));
            }
        }
        return results;
    }
    // Admin only
    public boolean addFlight(Flight flight){
        flights.add(flight);
        return true;
    }
    // Returns flight that last entered
    public Flight getFlight(){
        return flights.get(flights.size() - 1);
    }
    public boolean contains(String flightNo){
        for(int i = 0; i < flights.size(); i++){
            if(flights.get(i).getFlightNo().equals(flightNo))
                return false;
        }
        return true;
    }

}
