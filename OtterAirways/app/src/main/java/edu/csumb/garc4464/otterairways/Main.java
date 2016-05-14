package edu.csumb.garc4464.otterairways;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void CreateAccount(View view){
        Intent i = new Intent(this, Login.class);
        i.putExtra("TRANSACTION", "CREATE_ACCOUNT");
        startActivity(i);
    }
    public void ReserveSeat(View view){
        Intent i = new Intent(this, Insert.class);
        i.putExtra("TRANSACTION", "RESERVE_SEAT");
        startActivity(i);
    }
    public void CancelReservation(View view){
        Intent i = new Intent(this, Login.class);
        i.putExtra("TRANSACTION", "CANCEL");
        startActivity(i);
    }
    public void ManageSystem(View view){
        Intent i = new Intent(this, Login.class);
        i.putExtra("TRANSACTION", "MANAGE");
        startActivity(i);
    }
}
