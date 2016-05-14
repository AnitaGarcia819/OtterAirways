package edu.csumb.garc4464.otterairways;

/**
 * Created by anitagarcia on 5/11/16.
 */
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class ReservationCollection {
    private static ReservationCollection uniqueReservationCollection;
    HashMap<String,HashMap<String, Reservation>> reservations;
    public static Reservation pendingReservation;
    private int RESERVATION_NUM = 0;

    private ReservationCollection(){
        pendingReservation = new Reservation();
        reservations = new HashMap<String,HashMap<String, Reservation>>();
        /* For testing purposes
        reserve("!!Byun7","otter201",new Reservation(0,"monterey", "seattle","11:00 AM",2,"otter201",401.00));
        reserve("!!Byun7","otter102",new Reservation(1,"los angeles", "monterey","1:00 PM",6,"otter102",900.00));
        reserve("!!Byun7","otter301",new Reservation(2,"los angeles", "seattle","12:00 PM",1,"otter301",350.50));*/
    }
    public static void updatePendingReservation(Reservation reservation){
        //Log.d("PENDInG", reservation.toString());
        pendingReservation = reservation;
        //Log.d("PENDInG", pendingReservation.toString());
    }
    public static ReservationCollection getInstance(){
        if(uniqueReservationCollection == null){
            uniqueReservationCollection = new ReservationCollection();
        }
        return uniqueReservationCollection;
    }
    public boolean hasReservation(String username){
        if(reservations.get(username) == null){
            return false;
        }return true;
    }
    public int getRESERVATION_NUM(){return RESERVATION_NUM;}
    public boolean reserve(String username, String flightNo, Reservation reservation){
        // verify stuff
        if(reservations.get(username) == null){// If user has no reservation
            reservations.put(username, new HashMap<String, Reservation>());
            reservations.get(username).put(flightNo, reservation);
            RESERVATION_NUM++;
            return true;
        }else if(reservations.get(username) != null){
            // if they have at least one reservation
            reservations.get(username).put(flightNo, reservation);
            RESERVATION_NUM++;
            return true;
        }return false;
    }
    public boolean cancel(String username, String flightNo){
        // Increase FlightCollection capacity @ flightNo
        reservations.get(username).put(flightNo, null);
        return true;
    }
    public String getUserReservations(String username){
        // Verify
        String results = "";
        //ArrayList<Reservation> results = new ArrayList<Reservation>();
        for(Entry<String, Reservation> entry: reservations.get(username).entrySet()){
            Reservation value = entry.getValue();
            results += value.toString();
        }
        return results;
    }
    public Reservation getReservation(String username, String flightNo){
        if(reservations.get(username) != null){
            if(reservations.get(username).get(flightNo) != null){
                return reservations.get(username).get(flightNo);
            }
        }
        return null;
    }
    /*
    public Reservation makeReservation(String reservation){
        String[] res =  reservation.split(" ");
       // double totalPrice = price * Integer.parseInt(res[6]);
       // 0 otter102 Los Angeles Monterey 1:30 PM 6 900.0
       // 0 otter201 Monterey Seattle 11:00 AM 2
        //int resNumber, String departure, String arrival, String time,int noOfTickets, String flightNo
        Log.d("ReservationCollection", "makeRes" + reservation);
        Reservation result = new Reservation(Integer.parseInt(res[0]),res[2], res[3], res[4] + res[5], Integer.parseInt(res[6]), res[1], Double.parseDouble(res[7]));
        Log.d("ReservationCollection", result.toString());
        return result;
    }*/
}

