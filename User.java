package ATM;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    private final String firstName;
    private final String lastName;
    private final String uuid;
    private byte[] pinHash;//MD5 Hash
    private final ArrayList<Account> accounts;

    public User(String firstName, String lastName, String pin, Bank theBank){

        this.firstName = firstName;
        this.lastName = lastName;
        //MD5 Hash instead of real pin
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e){
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        this.uuid = theBank.getNewUserUUID();

        this.accounts = new ArrayList<>();

        System.out.println("New User " + lastName + ", " + firstName + " with ID " + this.uuid + " created.");

    }

    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    public String getUUID() {
        return this.uuid;
    }

    public boolean validatePin(String aPin){

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes(StandardCharsets.UTF_8)),this.pinHash);
        } catch (NoSuchAlgorithmException e){
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;

    }


    public String getFistName() {
        return this.firstName;
    }

    public void printAccountsSummary() {

        System.out.println("################################################");
        System.out.println("\n"+ this.firstName + "'s accounts summary");
        for (int i = 0; i < this.accounts.size(); i++) {
            System.out.println((i+1) + ") "+ this.accounts.get(i).getSummaryLine() ); //+ "\n");
        }
        System.out.println();

    }

    public int numAccounts() {
        return this.accounts.size();
    }

    public void printAcctTransHistory(int acctIdx) {
        this.accounts.get(acctIdx).printTransHistory();
    }

    public double getAcctBalance(int acctIdx) {
        return this.accounts.get(acctIdx).getBalance();
    }

    public String getAcctUUID(int acctIdx) {
        return this.accounts.get(acctIdx).getUUID();
    }

    public void addAcctTransaction(int acctIdx, double amount, String memo) {
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }
}
