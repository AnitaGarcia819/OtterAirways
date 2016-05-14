package edu.csumb.garc4464.otterairways;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class ViewFlight extends AppCompatActivity {
    TextView viewText;
    EditText viewEntry;
    Button viewButton;
    String TRANSACTION;
    String TRANSACTION_DEPARTURE;
    String TRANSACTION_ARRIVAL;
    String TRANSACTION_USERNAME;
    String inputEntry;
    int TRANSACTION_NO_TICKETS;
    FlightCollection flights;
    ReservationCollection reservations;
    TransactionsCollection transactions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flight);

        // Get flight and reservation collections
        flights = FlightCollection.getInstance();
        reservations = ReservationCollection.getInstance();
        transactions = TransactionsCollection.getInstance();

        // Page Text View, Edit Text, Button
        viewText = (TextView) findViewById(R.id.viewTextView);
        viewEntry = (EditText) findViewById(R.id.viewEdit);
        viewButton = (Button) findViewById(R.id.view_button);

        // Get String value of entry
        inputEntry = viewEntry.getText().toString();

        Log.d("VIEWFLIGHT_TEST", inputEntry);

        // Date inserted from Insert Activity
        TRANSACTION = getIntent().getExtras().getString("TRANSACTION");
        TRANSACTION_DEPARTURE = getIntent().getExtras().getString("DEPARTURE");
        TRANSACTION_ARRIVAL = getIntent().getExtras().getString("ARRIVAL");
        TRANSACTION_NO_TICKETS = getIntent().getExtras().getInt("NO_TICKETS");
        TRANSACTION_USERNAME = getIntent().getExtras().getString("USERNAME");


        // Log Text to test data values
        Log.d("VIEWFLIGHT_TEST", "DEPARTURE " + TRANSACTION_DEPARTURE);
        Log.d("VIEWFLIGHT_TEST", "ARRIVAL " + TRANSACTION_ARRIVAL);
        Log.d("VIEWFLIGHT_TEST", "NO_TICKETS " + TRANSACTION_NO_TICKETS);


        // Render proper button and label & data displayed
       // if(TRANSACTION != null)
        if(TRANSACTION.equals("RESERVE_SEAT")){
            viewButton.setText("RESERVE");
            viewEntry.setText("Flight No.");
            // Get available flights
            String output = flights.getAvailableFlights(TRANSACTION_DEPARTURE, TRANSACTION_ARRIVAL, TRANSACTION_NO_TICKETS);
            if(output.equals("There are no matching flights")) {
                // Error Alert
                AlertDialog alertDialog = new AlertDialog.Builder(ViewFlight.this).create();
                alertDialog = new AlertDialog.Builder(ViewFlight.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("This flight is not an option");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "E X I T",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Go to main menu
                                Intent i = new Intent(getBaseContext(), Main.class);
                                startActivity(i);
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }



            viewText.setText(output);

        }else if (TRANSACTION.equals("CANCEL")){
            viewButton.setText("CANCEL");
            viewEntry.setText("Flight No.");
            // Check to see if user name is valid
            if(!reservations.hasReservation(TRANSACTION_USERNAME)){
                // Error Alert
                AlertDialog alertDialog = new AlertDialog.Builder(ViewFlight.this).create();
                alertDialog = new AlertDialog.Builder(ViewFlight.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("This user has no reservations");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Go to main menu
                                Intent i = new Intent(getBaseContext(), Main.class);
                                startActivity(i);
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }else{
                // Display Reservations
                String userReservations = reservations.getUserReservations(TRANSACTION_USERNAME);
                viewText.setText(userReservations);
            }
        }
    }

    public void viewButton(View view){
        // Get String value of entry
        inputEntry = viewEntry.getText().toString();

        Log.d("VIEWFLIGHT_TEST", inputEntry);
        if(TRANSACTION.equals("RESERVE_SEAT")){

            // Check if flight is valid
            if(flights.isFlightAvailble(TRANSACTION_DEPARTURE, TRANSACTION_ARRIVAL, inputEntry , TRANSACTION_NO_TICKETS)){
                Log.d("VIEWFLIGHT", "flight is available");

                // Check if totalCap - cap entered >= 0
                if((flights.getFlight(inputEntry).getCapacity() - TRANSACTION_NO_TICKETS) >=0){
                    // Calculate total cost
                    double totalCost = TRANSACTION_NO_TICKETS * flights.getCost(inputEntry);
                    // Add to reservation collection
                    // Reservation constructor order of parameters: int resNumber, String departure, String arrival, Time time,int noOfTickets, String flightNo
                    Reservation newReservation = new Reservation(reservations.getRESERVATION_NUM(),TRANSACTION_DEPARTURE, TRANSACTION_ARRIVAL,flights.getFlight(inputEntry).getTime(),TRANSACTION_NO_TICKETS, inputEntry, totalCost);
                    Log.d("VIEWFLIGHT", newReservation.toString());

                    // Make this reservation pending
                    ReservationCollection.updatePendingReservation(newReservation);
                    // Log in
                    Intent i = new Intent(getBaseContext(), Login.class);
                    i.putExtra("TRANSACTION", "RESERVE_SEAT");
                    //i.putExtra("RESERVATION", newReservation.toString());
                    i.putExtra("FLIGHT_NO",inputEntry);
                    startActivity(i);

                }else
                    Log.d("VIEWFLIGHT", "there was an error");
            }else{
                // Error Alert
                AlertDialog alertDialog = new AlertDialog.Builder(ViewFlight.this).create();
                alertDialog = new AlertDialog.Builder(ViewFlight.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("This flight is not an option");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Go to main menu
                                //Intent i = new Intent(getBaseContext(), Main.class);
                                //startActivity(i);
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }else if (TRANSACTION.equals("CANCEL")){
            // Check for the flight NO. inserted
            // final so it can be used in inner dialog class
            final Reservation reservation = reservations.getReservation(TRANSACTION_USERNAME, inputEntry);
           // checks if user has this reservation or not
            if(reservation != null){
                //User does have reservation
                // Show Flight Confirm Dialog
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                Date date = new Date();
                                reservations.cancel(TRANSACTION_USERNAME, inputEntry);
                                transactions.log(new Cancelation(3, TRANSACTION_USERNAME, date, reservation));
                                //Intent i = new Intent(getBaseContext(), Insert.class);
                                //i.putExtra("TRANSACTION", "ADD_FLIGHT");
                                //startActivity(i);
                                dialog.dismiss();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                // i = new Intent(getBaseContext(), Main.class);
                                // startActivity(i);
                                dialog.dismiss();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewFlight.this);
                builder.setMessage("Confirm this deletion: \n" + reservations.getReservation(TRANSACTION_USERNAME, inputEntry).toString()).setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }else{
                // Error Alert
                AlertDialog alertDialog = new AlertDialog.Builder(ViewFlight.this).create();
                alertDialog = new AlertDialog.Builder(ViewFlight.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("This flight is not an option try again");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            }
        }
    }
}
