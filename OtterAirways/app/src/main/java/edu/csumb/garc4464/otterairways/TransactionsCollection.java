package edu.csumb.garc4464.otterairways;

/**
 * Created by anitagarcia on 5/11/16.
 */
import java.util.ArrayList;
import java.util.Map;


public class TransactionsCollection {
    private ArrayList<Transaction> transactions;
    private static TransactionsCollection uniqueTransactionsCollection;

    private TransactionsCollection(){
        transactions = new ArrayList<Transaction>();
    }
    public static TransactionsCollection getInstance(){
        if(uniqueTransactionsCollection == null){
            uniqueTransactionsCollection = new TransactionsCollection();
        }
        return uniqueTransactionsCollection;
    }
    public void log(Transaction transaction){
        transactions.add(transaction);
    }
    public Transaction getTransaction(){
        return transactions.get(transactions.size()-1);
    }
    public String getTransactionsReport(){
        // Verify
        String results = "";
        for(int i = 0; i < transactions.size(); i++){
           results += transactions.get(i).toString();
        }
        return results;
    }
}
