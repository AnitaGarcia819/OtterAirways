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
import android.widget.EditText;

public class Insert extends AppCompatActivity {
    FlightCollection flights;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        flights = FlightCollection.getInstance();

    }
    public void insertButton(View view) {
        String Error = "UNKNOWN";
        // Input Fields Data
        EditText flightNo = (EditText) findViewById(R.id.flightNo);
        EditText departure = (EditText) findViewById(R.id.departure);
        EditText arrival = (EditText) findViewById(R.id.arrival);
        EditText departureTime = (EditText) findViewById(R.id.departureTime);
        EditText flightCapacity = (EditText) findViewById(R.id.flightCapacity);
        EditText price = (EditText) findViewById(R.id.price);
        final String inputFlightNo = flightNo.getText().toString();
        final String inputDeparture = departure.getText().toString();
        final String inputArrival = arrival.getText().toString();
        final String inputDepartureTime = departureTime.getText().toString();
        final int inputFlightCapacity = Integer.parseInt(flightCapacity.getText().toString());
        final double inputPrice = Double.parseDouble(price.getText().toString());

        Log.d("INSERT_TEST", "Flight Capacity" + inputFlightCapacity);
        Log.d("INSERT_TEST", "Flight Price" + inputPrice);
        // Place info in a flight Object

        // Check if flight is duplicated
        if (!flights.contains(inputFlightNo)) {
            // Show Confirm Dialog
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            Log.d("INSERT_TEST", "flight is not in Flight Collection");

                            // Input flight data into FlightCollection
                            flights.addFlight((new Flight(inputFlightNo, inputDeparture, inputArrival, inputDepartureTime, inputPrice, inputFlightCapacity)));

                            // Log flight recently added to test
                            Log.d("INSERT_TEST", " " + flights.getFlight().toString());

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
            AlertDialog.Builder builder = new AlertDialog.Builder(Insert.this);
            builder.setMessage("DATA").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();


        } else {
            // Display error alert
            AlertDialog alertDialog = new AlertDialog.Builder(Insert.this).create();
            alertDialog = new AlertDialog.Builder(Insert.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("This flight " + flightNo + " already exists.");
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
