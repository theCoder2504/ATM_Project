package ATM;

import java.util.Scanner;


public class ATM {
    public static void main(String[] args) {

        Bank theBank = new Bank("Bank of England");

        User aUser = theBank.addUser("Max","Mustermann","1234");

        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true){

            curUser = ATM.mainMenuPrompt(theBank);

            ATM.printUserMenu(curUser);

        }
    }

    private static void printUserMenu(User theUser) {

        Scanner sc = new Scanner(System.in);

        theUser.printAccountsSummary();

        int choice;

        do {
            System.out.println("Welcome " + theUser.getFistName() + ", what would you like to do?");
            System.out.println("   1) Show account transaction history");
            System.out.println("   2) Withdraw");
            System.out.println("   3) Deposit");
            System.out.println("   4) Transfer");
            System.out.println("   5) Quit");
            System.out.println("\nEnter choice: ");
            choice = Integer.parseInt(sc.next());

            if (choice < 1 || choice > 5){
                System.out.println("Invalid choice. Please choose 1-5");
            }

        } while (choice < 1 || choice > 5);


        switch (choice){

            case 1:
                ATM.showTransHistory(theUser);
                break;
            case 2:
                ATM.withdrawFunds(theUser);
                break;
            case 3:
                ATM.depositFunds(theUser);
                break;
            case 4:
                ATM.transferFunds(theUser);
                break;
        }


        if (choice != 5){
            ATM.printUserMenu(theUser);
        } else {
            System.out.println("Thank you for using our service");
            System.exit(0);
        }
    }

    private static void depositFunds(User theUser) {

        Scanner sc = new Scanner(System.in);

        int Acct;
        double amount;
        String memo;
        int numAccts = theUser.numAccounts();

        do {
            System.out.println("Enter the number (1-" + numAccts + ") of the account\n" +
                    "to transfer to: ");
            Acct = sc.nextInt()-1;
            if (Acct < 0 || Acct >= numAccts){
                System.out.println("Invalid account. Please try again.");
            }
        } while (Acct < 0 || Acct >= numAccts);

        do {
            System.out.println("Enter the amount to transfer : ");
            amount = sc.nextDouble();
            if (amount < 0){
                System.out.println("Amount must be greater than zero.");
            }
        } while (amount < 0);

        sc.nextLine();

        System.out.println("Enter the memo: ");
        memo = sc.nextLine();

        theUser.addAcctTransaction(Acct, amount, memo);
    }

    private static void withdrawFunds(User theUser) {

        Scanner sc = new Scanner(System.in);

        int Acct;
        double amount;
        double acctBal;
        String memo;

        do {
            System.out.println("Enter the number (1-" + theUser.numAccounts() + ") of the account\n" +
                    "to transfer from: ");
            Acct = sc.nextInt()-1;
            if (Acct < 0 || Acct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (Acct < 0 || Acct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(Acct);

        do {
            System.out.printf("Enter the amount to transfer (max €%.02f): ", acctBal);
            amount = sc.nextDouble();
            if (amount < 0){
                System.out.println("Amount must be greater than zero.");
            } else if (amount > acctBal){
                System.out.println("Amount must not be greater than");
                System.out.printf("balance of €%.2f.\n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);

        sc.nextLine();

        System.out.println("Enter the memo: ");
        memo = sc.nextLine();

        theUser.addAcctTransaction(Acct, -1*amount, memo);
    }

    private static void transferFunds(User theUser) {

        Scanner sc = new Scanner(System.in);

        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        do {
            System.out.println("Enter the number (1-" + theUser.numAccounts() + ") of the account\n" +
                    "to transfer from: ");
            fromAcct = sc.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

        do {
            System.out.println("Enter the number (1-" + theUser.numAccounts() + ") of the account\n" +
                    "to transfer to: ");
            toAcct = sc.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts() || toAcct == fromAcct){
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts() || toAcct == fromAcct);

        do {
            System.out.printf("Enter the amount to transfer (max €%.02f): ", acctBal);
            amount = sc.nextDouble();
            if (amount < 0){
                System.out.println("Amount must be greater than zero.");
            } else if (amount > acctBal){
                System.out.println("Amount must not be greater than");
                System.out.printf("balance of €%.2f.\n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);

        theUser.addAcctTransaction(fromAcct, -1*amount, "Transfer to account " + theUser.getAcctUUID(toAcct));

        theUser.addAcctTransaction(toAcct, amount, "Transfer from account " + theUser.getAcctUUID(fromAcct));
    }

    private static void showTransHistory(User theUser) {

        Scanner sc = new Scanner(System.in);

        int theAcct;

        do {

            System.out.println("Enter the number (1-" + theUser.numAccounts() + ") of the account\n" +
                    "whose transactions you want to see: ");
            theAcct = Integer.parseInt(sc.next())-1;
            if (theAcct < 0 || theAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }

        } while (theAcct < 0 || theAcct >= theUser.numAccounts());

        theUser.printAcctTransHistory(theAcct);

    }

    private static User mainMenuPrompt(Bank theBank) {

        Scanner sc = new Scanner(System.in);

        String userID;
        String pin;
        User authUser;

        do {

            System.out.println("\nWelcome to " + theBank.getName() +"\n");
            System.out.println("Enter user ID: ");
            userID = sc.nextLine();
            System.out.println("Enter pin: ");
            pin = sc.nextLine();

            authUser = theBank.userLogin(userID, pin);

            if (authUser == null){
                System.out.println("Incorrect ID/pin combination. \n" +
                        "Please try again.");
            }

        } while (authUser == null);
        return authUser;
    }

}
