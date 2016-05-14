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

public class Insert extends AppCompatActivity {
    FlightCollection flights;
    String TRANSACTION;
    Button insertButton;
    TextView textView;
    EditText departure;
    EditText arrival;
    EditText numTickets;
    EditText departureTime;
    EditText flightCapacity;
    EditText price;
    EditText flightNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        // Get access to the flights
        flights = FlightCollection.getInstance();

        // Page Button and Label and Edit Views
        insertButton = (Button) findViewById(R.id.insert_button);
        textView = (TextView) findViewById(R.id.insertTextView);
        departure = (EditText) findViewById(R.id.departure);
        arrival = (EditText) findViewById(R.id.arrival);
        numTickets = (EditText) findViewById(R.id.numTickets);
        departureTime = (EditText) findViewById(R.id.departureTime);
        flightCapacity = (EditText) findViewById(R.id.flightCapacity);
        price = (EditText) findViewById(R.id.price);
        flightNo = (EditText) findViewById(R.id.flightNo);

        // Transaction button pressed on from Main Page or Insert Activity
        TRANSACTION = getIntent().getExtras().getString("TRANSACTION");

        // Render proper button and label and edit views
        if(TRANSACTION.equals("RESERVE_SEAT")){
            insertButton.setText("RESERVE SEAT");
            textView.setText("VIEW FLIGHTS");
            flightNo.setVisibility(View.INVISIBLE);
            departureTime.setVisibility(View.INVISIBLE);
            flightCapacity.setVisibility(View.INVISIBLE);
            price.setVisibility(View.INVISIBLE);
            numTickets.setVisibility(View.VISIBLE);

        }else if(TRANSACTION.equals("ADD_FLIGHT")){
            insertButton.setText("ADD FLIGHT");
            textView.setText("ADD FLIGHT");
        }

    }
    public void insertButton(View view) {
        String Error = "UNKNOWN";
        // Intent and Bundle for View Activity
        Intent i = new Intent(getBaseContext(), ViewFlight.class);

        if(TRANSACTION.equals("RESERVE_SEAT")){
            // Data Inserted
            departure = (EditText) findViewById(R.id.departure);
            arrival = (EditText) findViewById(R.id.arrival);
            numTickets = (EditText) findViewById(R.id.numTickets);

            // Get input from user
            final String inputDeparture = departure.getText().toString();
            final String inputArrival = arrival.getText().toString();
            int inputNumTickets = Integer.parseInt(numTickets.getText().toString());

            // Send data to View Activity
            i.putExtra("DEPARTURE", inputDeparture);
            i.putExtra("ARRIVAL", inputArrival);
            i.putExtra("NO_TICKETS", inputNumTickets);

            if(inputNumTickets <= flights.getMaxTickets()){

                // Check if flight is valid
                // Send Flight into to View Activity
                i.putExtra("TRANSACTION", "RESERVE_SEAT");
                startActivity(i);
            }else{

                    // Display error alert
                    AlertDialog alertDialog = new AlertDialog.Builder(Insert.this).create();
                    alertDialog = new AlertDialog.Builder(Insert.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("You may only reserve 7 tickets at a time");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

            }


        }else if (TRANSACTION.equals("ADD_FLIGHT")){
            // Input Fields Data
            flightNo = (EditText) findViewById(R.id.flightNo);
            departure = (EditText) findViewById(R.id.departure);
            arrival = (EditText) findViewById(R.id.arrival);
            departureTime = (EditText) findViewById(R.id.departureTime);
            flightCapacity = (EditText) findViewById(R.id.flightCapacity);
            price = (EditText) findViewById(R.id.price);
            final String inputFlightNo = flightNo.getText().toString();
            final String inputDeparture = departure.getText().toString();
            final String inputArrival = arrival.getText().toString();
            final String inputDepartureTime = departureTime.getText().toString();
            final int inputFlightCapacity = Integer.parseInt(flightCapacity.getText().toString());
            final double inputPrice = Double.parseDouble(price.getText().toString());

            Log.d("INSERT_TEST", "Flight Capacity" + inputFlightCapacity);
            Log.d("INSERT_TEST", "Flight Price" + inputPrice);

            // Place info in a flight Object
            final Flight flight = new Flight(inputFlightNo.toLowerCase(), inputDeparture.toLowerCase(), inputArrival.toLowerCase(), inputDepartureTime, inputPrice, inputFlightCapacity);
            Log.d("INSERT_TEST", flight + " ");

            // Check if flight is duplicated
            if (!flights.contains(inputFlightNo.toLowerCase())){
                // Show Confirm Dialog
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                Log.d("INSERT_TEST", "flight is not in Flight Collection");

                                // Input flight data into FlightCollection
                                flights.addFlight(flight);

                                // Log flight recently added to test
                                //Log.d("INSERT_TEST", " " + flight);

                                // Go to Main Menu
                                Intent i = new Intent(getBaseContext(), Main.class);
                                // Go to homepagestartActivity(i);
                                startActivity(i);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                i = new Intent(getBaseContext(), Main.class);
                                startActivity(i);
                                dialog.dismiss();
                                break;
                        }
                    }
                };
                String output = "Do you wish to Add: \n"+
                        "Flight No:" + flight.getFlightNo() + " \n" +
                        "Departure: " + flight.getDeparture() + " \n" +
                        "Arrival: " + flight.getArrival() + " \n" +
                        "Departure Time: " + flight.getTime() + " \n" +
                        "Price: $" + flight.getPrice() + "\nCapacity:  " + flight.getCapacity();
                AlertDialog.Builder builder = new AlertDialog.Builder(Insert.this);
                builder.setMessage(output).setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            } else {
                // Display error alert
                AlertDialog alertDialog = new AlertDialog.Builder(Insert.this).create();
                alertDialog = new AlertDialog.Builder(Insert.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("This flight " + flight.getFlightNo() + " already exists.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(getBaseContext(), Main.class);
                                startActivity(i);
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }
    }
}
