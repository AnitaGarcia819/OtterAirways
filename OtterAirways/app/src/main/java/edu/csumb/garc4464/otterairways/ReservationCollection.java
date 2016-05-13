package edu.csumb.garc4464.otterairways;

/**
 * Created by anitagarcia on 5/11/16.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


public class ReservationCollection {
    private static ReservationCollection uniqueReservationCollection;
    HashMap<String,HashMap<String, Reservation>> reservations;

    private ReservationCollection(){
        reservations = new HashMap<String,HashMap<String, Reservation>>();
    }
    private static ReservationCollection getInstance(){
        if(uniqueReservationCollection == null){
            uniqueReservationCollection = new ReservationCollection();
        }
        return uniqueReservationCollection;
    }
    public boolean reserve(String username, String flightNo, Reservation reservation){
        // verify stuff
        if(reservations.get(username) == null){// If user has no reservation
            reservations.put(username, new HashMap<String, Reservation>());
            reservations.get(username).put(flightNo, reservation);
            return true;
        }else if(reservations.get(username) != null){
            // if they have at least one reservation
            reservations.get(username).put(flightNo, reservation);
            return true;
        }return false;
    }
    public boolean cancel(String username, String flightNo){
        // Check if it is a Reservation
        // Check if they are a user
        // Increase FlightCollection capacity @ flightNo
        reservations.get(username).put(flightNo, null);
        return true;
    }
    public ArrayList<Reservation> getUserReservations(String username){
        // Verify
        ArrayList<Reservation> results = new ArrayList<Reservation>();
        for(Entry<String, Reservation> entry: reservations.get(username).entrySet()){
            Reservation value = entry.getValue();
            results.add(value);
        }
        return results;
    }
}

