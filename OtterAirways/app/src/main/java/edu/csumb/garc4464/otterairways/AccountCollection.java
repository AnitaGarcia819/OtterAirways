package edu.csumb.garc4464.otterairways;

/**
 * Created by anitagarcia on 5/11/16.
 */
import android.accounts.Account;
import android.util.Log;

import java.util.HashMap;


public class AccountCollection {
    private static AccountCollection uniqueAccountCollection;
    HashMap<String, Customer> customers;

    private AccountCollection(){
        customers = new HashMap<String, Customer>();
        customers.put("A@lice5",new Customer("A@lice5", "@cSit100"));
        customers.put("$BriAn7",new Customer("BriAn7", "123aBc##"));
        customers.put("!chriS12!",new Customer("!chriS12!", "CHrIS12!!"));
        //customers.put("!!Byun7",new Customer("!!Byun7", "!!Byun7"));
    }
    public static AccountCollection getInstance(){
        if(uniqueAccountCollection == null){
            uniqueAccountCollection = new AccountCollection();
        }
        return uniqueAccountCollection;
    }
    public boolean add(String username, String password){
        // Make sure there is verification
        Customer newCustomer = new Customer(username, password);
        customers.put(username, newCustomer);
        return true;
    }
    public boolean isUser(String username){
       // Log.d("(isUser)", "result:" + customers.containsKey(username));
        return customers.containsKey(username);
    }
    public Customer getAccount(String userName){
        return customers.get(userName);
    }
}
