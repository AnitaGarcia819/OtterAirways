package edu.csumb.garc4464.otterairways;

/**
 * Created by anitagarcia on 5/11/16.
 */
import android.util.Log;

import java.util.ArrayList;


public class FlightCollection {
    private final int MAX_NUM_TICKETS = 7;
    private ArrayList<Flight> flights;
    private static FlightCollection uniqueFlightCollection;
    private ArrayList<String> cities = new ArrayList<String>();

    private FlightCollection(){
        flights = new ArrayList<Flight>();
        cities.add("monterey");
        cities.add("los angeles");
        cities.add("seattle");

        flights.add(new Flight("otter101", "monterey", "los angeles", "10:30 AM", 150.00, 10));
        flights.add(new Flight("otter102", "los angeles", "monterey", "1:30 PM", 150.00, 10));
        flights.add(new Flight("otter201", "monterey", "seattle", "11:00 AM", 200.50, 5));
        flights.add(new Flight("otter205", "monterey", "seattle", "3:45 AM", 150.00, 15));
        flights.add(new Flight("otter202", "seattle", "monterey", "2:10 PM", 200.50, 5));
    }
    public static FlightCollection getInstance(){
        if(uniqueFlightCollection == null){
            uniqueFlightCollection = new FlightCollection();
        }
        return uniqueFlightCollection;
    }
    public boolean isCity(String cityName){
        return cities.contains(cityName.toLowerCase());
    }
    public int getMaxTickets(){
        return MAX_NUM_TICKETS;
    }

    public String getAvailableFlights(String departure, String arrival, int no){
        // Normalize input
        departure = departure.toLowerCase();
        arrival = arrival.toLowerCase();

        // Test Data inserted
        //Log.d("FlightCollection", "(getAvFlight)" + departure + " | " + arrival + " | " + no);

        // Output to be returned
        String output = "Available Flights: \n\n" +
                "Flight No.|Departure|Arrival|Dep. Time|Price|Cap. \n";;
        boolean noResults = true;
        for(int i = 0; i < flights.size(); i++){
           /* Log.d("FlightCollection", "(inside loop)" +flights.get(i).getDeparture()
                    + " | " + flights.get(i).getArrival() + " | " + flights.get(i).getCapacity());*/
            if(flights.get(i).getDeparture().equals(departure)
                    && flights.get(i).getArrival().equals(arrival)
                    && no <= (flights.get(i).getCapacity() )){
                //Log.d("FlghtCollection", "==");
                output += flights.get(i).toString() + "\n";
                noResults = false;
            }
        }
        if(noResults)
            return "There are no matching flights";
        return output;
    }
    // Admin only
    public boolean addFlight(Flight flight){
        flights.add(flight);
        return true;
    }
    // Returns flight that last entered
    public Flight getFlight(String flightNo){
        for(int i = 0; i < flights.size(); i++){
            if(flights.get(i).getFlightNo().equals(flightNo)){
                return flights.get(i);
            }
        }
        return null;
    }
    public boolean contains(String flightNo){
        for(int i = 0; i < flights.size(); i++){
            if(flights.get(i).getFlightNo().equals(flightNo))
                return true;
        }
        return false;
    }
    public boolean isFlightAvailble(String departure, String arrival, String flightNo, int no){
        // Normalize
        departure = departure.toLowerCase();
        arrival = arrival.toLowerCase();
        flightNo = flightNo.toLowerCase();

        //Log.d("FlightCollection", "(input)" +departure
              //  + " | " + arrival + " | " + flightNo + " | " + no);
        for(int i = 0; i < flights.size(); i++){
            // Log.d("FlightCollection", "(isFlightAvailavle)" +flights.get(i).getDeparture()
                    //+ " | " + flights.get(i).getArrival() + " | "+ flights.get(i).getFlightNo() +" | " + flights.get(i).getCapacity());
            if(flights.get(i).getDeparture().equals(departure)
                    && flights.get(i).getArrival().equals(arrival)
                    && no <= (flights.get(i).getCapacity() )
                    && flights.get(i).getFlightNo().equals(flightNo)){
                    return true;
            }
        }
        return false;
    }
    public void updateFlight(String flightNo, int no){
       // Log.d("DEBUG", "NUM OF TICKETS:(update) " + no);

        int currentCapacity;
        // Changes capacity
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getFlightNo().equals(flightNo)){
                currentCapacity = flights.get(i).getCapacity();
                //Log.d("DEBUG", "NUM OF TICKETS:(current cap) " + no);
                //Log.d("DEBUG", "NUM OF TICKETS:(current cap -  no ) "+  (currentCapacity - no));

                flights.get(i).setCapacity(currentCapacity - no);
            }
        }
    }
    public double getCost(String flightNo) {
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getFlightNo().equals(flightNo))
                return flights.get(i).getPrice();
        }
        return 0.0;
    }
}
