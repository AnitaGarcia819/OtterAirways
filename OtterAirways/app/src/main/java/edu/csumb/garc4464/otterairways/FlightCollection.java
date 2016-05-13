package edu.csumb.garc4464.otterairways;

/**
 * Created by anitagarcia on 5/11/16.
 */
import java.util.ArrayList;


public class FlightCollection {
    private int MAX_NUM_TICKETS;
    private ArrayList<Flight> flights;
    private static FlightCollection uniqueFlightCollection;
    private ArrayList<String> cities = new ArrayList<String>();

    private FlightCollection(){
        flights = new ArrayList<Flight>();
        cities.add("monterey");
        cities.add("los angeles");
        cities.add("seattle");

        flights.add(new Flight("Otter101", "Monterey", "Los Angeles", "10:30 AM", 150.00, 10));
        flights.add(new Flight("Otter102", "Los Angeles", "Monterey", "1:30 PM", 150.00, 10));
        flights.add(new Flight("Otter201", "Monterey", "Seattle", "11:00 AM", 200.50, 5));
        flights.add(new Flight("Otter205", "Monterey", "Seattle", "3:45 AM", 150.00, 15));
        flights.add(new Flight("Otter202", "Seattle", "Monterey", "2:10 PM", 200.50, 5));
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
    public ArrayList<Flight> getAvailableFlights(String departure, String arrival, int no){
        ArrayList<Flight> results = new ArrayList<Flight>();
        for(int i = 0; i < flights.size(); i++){
            if(flights.get(i).getDeparture().equals(departure)
                    && flights.get(i).getDeparture().equals(arrival)
                    && flights.get(i).getCapacity() == 1){
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
                return true;
        }
        return false;
    }
}
