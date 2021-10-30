package ATM;

import java.time.Instant;
import java.util.Date;

public class Transaction {
    private final double amount;
    private final String timestamp;
    private String memo;
    private final Account inAccount;

    public Transaction(double amount, Account inAccount){

        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = getTimestamp();
        this.memo = "";


    }

    private String getTimestamp() {
        Date date = new Date();
        Instant newTimestamp = date.toInstant();
        String[] x = newTimestamp.toString().split("\\.");
        String test = x[0].replace("T"," ");
        return test;
    }

    public Transaction(double amount, String memo, Account inAccount){

        this(amount,inAccount);

        this.memo = memo;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getSummaryLine() {

        if (this.amount >= 0){
            return String.format("%s : €%.02f : %s", this.timestamp, this.amount, this.memo);
        } else {
            return String.format("%s : €(%.02f) : %s", this.timestamp, this.amount, this.memo);
        }

    }
}
