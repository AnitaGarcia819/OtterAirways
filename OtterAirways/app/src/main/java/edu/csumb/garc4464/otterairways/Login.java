package edu.csumb.garc4464.otterairways;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    private AccountCollection accounts;
    private TransactionsCollection transactions;
    private ReservationCollection reservations;
    private FlightCollection flights;
    private String TRANSACTION;
    private String RESERVATION;
    private String FLIGHT_NO;
    Button submitButton;
    TextView textView;
    int signUpAttempts = 0;
    private String ADMIN_USERNAME = "!admiM2";
    private String ADMIN_PASSWORD = "!admiM2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize transaction and account collections
        accounts = AccountCollection.getInstance();
        transactions = TransactionsCollection.getInstance();
        reservations = ReservationCollection.getInstance();
        flights = FlightCollection.getInstance();

        // Page Button and Label
        submitButton = (Button) findViewById(R.id.submit_button);
        textView = (TextView) findViewById(R.id.loginTextView);

        // Button Pressed on from Main Page
        TRANSACTION = getIntent().getExtras().getString("TRANSACTION");
        RESERVATION = getIntent().getExtras().getString("RESERVATION");
        FLIGHT_NO = getIntent().getExtras().getString("FLIGHT_NO");

        // Render proper button and label
        if(TRANSACTION.equals("CANCEL") || TRANSACTION.equals("MANAGE") || TRANSACTION.equals("RESERVE_SEAT")){
            submitButton.setText("LOGIN");
            textView.setText("Login");

        }else if(TRANSACTION.equals("CREATE_ACCOUNT")){
            submitButton.setText("SIGN UP");
            textView.setText("Sign Up");
        }
    }
    // Logs in or Signs up
    public void submitButton(View view){
        AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();
        // Intent to new class
        Intent i = new Intent(this, Main.class);
        // Username and Password
        EditText userName = (EditText) findViewById(R.id.viewEdit);
        EditText password = (EditText) findViewById(R.id.password);
        final String inputUsername = userName.getText().toString();
        String inputPassword = password.getText().toString();
        // Log in attempts
        int loginAttempts = 0;

        // Verify if sign up or login
        if(TRANSACTION.equals("CREATE_ACCOUNT")){
            // Input Validation
            Pattern pat = Pattern.compile("(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$])");
            Matcher pattern_username = pat.matcher(inputUsername);
            Matcher pattern_password = pat.matcher(inputPassword);
            if (!pattern_username.find() || !pattern_password.find()) {
                // Increase attempts
                signUpAttempts++;
                // Alert
                alertDialog = new AlertDialog.Builder(Login.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Username/Password must contain at least:\n 1 special character (@!#$)\n 1 upper case \n 1 lower case \n 1 digit");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // If Attempts are 2
                                if (signUpAttempts == 2) {
                                    signUpAttempts = 0;
                                    // Go to main menu
                                    Intent i = new Intent(getBaseContext(), Main.class);
                                    startActivity(i);
                                    dialog.dismiss();
                                }
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            // Verify if user doesn't already exist
           else if(!accounts.isUser(inputUsername)){
                Log.d("submitButton", "User does not exist");
                // Add to AccountCollection
                accounts.add(inputUsername, inputPassword);
                // Display Accounts for testing
                Log.d("LOGIN_TEST", "account" + accounts.getAccount(inputUsername).toString());

                // Get cuurent time
                Date date = new Date();

                // Add transaction to TransactionCollection
                Transaction transaction = new NewAccount(1, inputUsername, date);
                transactions.log(transaction);

                // Display Transactions for testing
                Log.d("LOGIN_TEST","transaction" + transactions.getTransaction().toString() );

                // Display a successful add
                alertDialog = new AlertDialog.Builder(Login.this).create();
                alertDialog.setTitle("Success");
                alertDialog.setMessage("Your account has successfully been added");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(getBaseContext(), Main.class);
                                startActivity(i);
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                // Go to Main Activity
            }else{// If user does exist already
                signUpAttempts++;
                alertDialog = new AlertDialog.Builder(Login.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("This username is taken already");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // If Attempts are 2
                                if (signUpAttempts == 2) {
                                    signUpAttempts = 0;
                                    Intent i = new Intent(getBaseContext(), Main.class);
                                    startActivity(i);
                                    dialog.dismiss();
                                }
                                // Go to Main
                                // If Attempts < 2
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

                }
        }else if(TRANSACTION.equals("CANCEL")){
            // Verify Username Exists
            if(accounts.isUser(inputUsername)){
                // Verify Password
                if(accounts.getAccount(inputUsername).getPassword().equals(inputPassword)){
                    i = new Intent(getBaseContext(), ViewFlight.class);

                    startActivity(i);
                }else{
                    //If Password is wrong
                    signUpAttempts++;
                    alertDialog = new AlertDialog.Builder(Login.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Wrong username/password");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // If Attempts are 2
                                    if (signUpAttempts == 2) {
                                        signUpAttempts = 0;
                                        // Go to main menu
                                        Intent i = new Intent(getBaseContext(), Main.class);
                                        startActivity(i);
                                        dialog.dismiss();
                                    }
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }else{
                signUpAttempts++;
                // Alert
                alertDialog = new AlertDialog.Builder(Login.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("User name Does not Exist");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // If Attempts are 2
                                if (signUpAttempts == 2) {
                                    signUpAttempts = 0;
                                    Intent i = new Intent(getBaseContext(), Main.class);
                                    startActivity(i);
                                    dialog.dismiss();
                                }
                                // Go to Main
                                // If Attempts < 2
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }else if(TRANSACTION.equals("MANAGE")){
            if(inputUsername.equals(ADMIN_USERNAME) && inputPassword.equals(ADMIN_PASSWORD)){
                // Display transactions
                alertDialog = new AlertDialog.Builder(Login.this).create();
                alertDialog.setTitle("Transaction Report");
                alertDialog.setMessage(transactions.getTransactionsReport());
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Show Flight Confirm Dialog
                                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which){
                                            case DialogInterface.BUTTON_POSITIVE:
                                                //Yes button clicked
                                                Intent i = new Intent(getBaseContext(), Insert.class);
                                                i.putExtra("TRANSACTION", "ADD_FLIGHT");
                                                startActivity(i);
                                                dialog.dismiss();
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                builder.setMessage("Do you wish to add flight?").setPositiveButton("Yes", dialogClickListener)
                                        .setNegativeButton("No", dialogClickListener).show();
                               // dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }else {
                signUpAttempts++;
                // Alert
                alertDialog = new AlertDialog.Builder(Login.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Wrong username/password");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // If Attempts are 2
                                if (signUpAttempts == 2) {
                                    signUpAttempts = 0;
                                    Intent i = new Intent(getBaseContext(), Main.class);
                                    startActivity(i);
                                    dialog.dismiss();
                                }
                                // Go to Main
                                // If Attempts < 2
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }else if(TRANSACTION.equals("RESERVE_SEAT")){
            // Verify Username Exists
            if(accounts.isUser(inputUsername)){
                // Verify Password
                if(accounts.getAccount(inputUsername).getPassword().equals(inputPassword)){
                    // TODO:
                    // Show flight info
                    // Show Flight Confirm Dialog
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    // Yes button clicked
                                    // Get pending reservation;
                                    //ReservationCollection.pendingReservation;


                                    //Reservation reservation = reservations.makeReservation(RESERVATION);
                                    Log.d("LOGIN_TEST", "NUM OF TICKETS: " + ReservationCollection.pendingReservation.getNumOfTickets());

                                    reservations.reserve(inputUsername, FLIGHT_NO, ReservationCollection.pendingReservation);
                                    Log.d("LOGIN_TEST", "reseration added " + ReservationCollection.pendingReservation.toString());
                                    // Update Capacity of flight
                                    //flights.updateFlight(FLIGHT_NO, reservation.getNumOfTickets());
                                    // Log reservations
                                    Date date = new Date();
                                    transactions.log(new ReserveSeat(2, inputUsername, date, ReservationCollection.pendingReservation));
                                    // go home
                                    ReservationCollection.pendingReservation = null;
                                    Intent intent = new Intent(getBaseContext(), Main.class);
                                    startActivity(intent);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    // Go home OR dismiss
                                    //i = new Intent(getBaseContext(), Main.class);
                                    //startActivity(i);
                                    dialog.dismiss();
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    builder.setMessage("Confirm this reservation: \n" + ReservationCollection.pendingReservation.toString()).setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();

                }else{
                    //If Password is wrong
                    signUpAttempts++;
                    alertDialog = new AlertDialog.Builder(Login.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Wrong username/password");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // If Attempts are 2
                                    if (signUpAttempts == 2) {
                                        signUpAttempts = 0;
                                        // Go to main menu
                                        Intent i = new Intent(getBaseContext(), Main.class);
                                        startActivity(i);
                                        dialog.dismiss();
                                    }
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }else{
                signUpAttempts++;
                // Alert
                alertDialog = new AlertDialog.Builder(Login.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("User name Does not Exist");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // If Attempts are 2
                                if (signUpAttempts == 2) {
                                    signUpAttempts = 0;
                                    Intent i = new Intent(getBaseContext(), Main.class);
                                    startActivity(i);
                                    dialog.dismiss();
                                }
                                // Go to Main
                                // If Attempts < 2
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }

        }
    }
}
