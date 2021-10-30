package ATM;

import java.util.ArrayList;

public class Account {
    private final String name;
//    private double balance;
    private final String uuid;
    private final User holder;
    private final ArrayList<Transaction> transactions;

    public Account(String name, User holder, Bank theBank){
        this.name = name;
        this.holder = holder;

        this.uuid = theBank.getNewAccountUUID();

        this.transactions = new ArrayList<>();

    }

    public String getUUID() {
        return this.uuid;
    }

    public String getSummaryLine() {

        double balance = this.getBalance();

        if (balance >= 0){
    //        return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
            return String.format("%s : €%.02f : %s", this.uuid, balance, this.name);
        } else {
    //        return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
            return String.format("%s : €(%.02f) : %s", this.uuid, balance, this.name);
        }
    }

    public double getBalance() {

        double balance = 0;

        for (Transaction t : this.transactions) {
            balance += t.getAmount();
        }
        return balance;
    }

    public void printTransHistory() {

        System.out.println("\nTransaction history of the account " + this.uuid + ":\n");
        for (int t = this.transactions.size()-1; t >= 0; t--) {
            System.out.println("\t" + this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }

    public void addTransaction(double amount, String memo) {
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);
    }
}
