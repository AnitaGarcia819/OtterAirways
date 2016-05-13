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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    private AccountCollection accounts;
    private TransactionsCollection transactions;
    private String TRANSACTION;
    Button submitButton;
    TextView textView;
    int signUpAttempts = 0;
    private String ADMIN_USERNAME = "a";
    private String ADMIN_PASSWORD = "a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize transaction and account collections
        accounts = AccountCollection.getInstance();
        transactions = TransactionsCollection.getInstance();

        // Page Button and Label
        submitButton = (Button) findViewById(R.id.submit_button);
        textView = (TextView) findViewById(R.id.loginTextView);

        // Button Pressed on from Main Page

        TRANSACTION = getIntent().getExtras().getString("TRANSACTION");

        // Render proper button and label
        if(TRANSACTION.equals("CANCEL") || TRANSACTION.equals("MANAGE")){
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
        EditText userName = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        String inputUsername = userName.getText().toString();
        String inputPassword = password.getText().toString();
        // Log in attempts
        int loginAttempts = 0;

        // Verify if sign up or login
        if(TRANSACTION.equals("CREATE_ACCOUNT")){

            // TODO: Input Validation
            Pattern pat = Pattern.compile("(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$])");
            String  str = inputUsername;
            Matcher m = pat.matcher(str);
            if (!m.find()) {

                Log.d("submitButton", "User does exist");
                // TODO: If user exists

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
                }
        }else if(TRANSACTION.equals("CANCEL")){
            // verify username is not taken
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
        }
    }

}
