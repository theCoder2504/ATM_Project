package ATM;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private final String name;
    private final ArrayList<User> users;
    private final ArrayList<Account> accounts;

    public Bank(String name){
        this.name = name;
        this.users = new ArrayList<>();
        this.accounts = new ArrayList<>();
    }

    public String getNewUserUUID(){

        String uuid;
        Random rdm = new Random();
        int len = 6;
        boolean isUnique;

        do{
            uuid = "";
            isUnique = false;
            for (int i = 0; i < len; i++) {
                uuid += ((Integer)rdm.nextInt(10)).toString();
            }
            for (User u : this.users) {
                if (uuid.compareTo(u.getUUID()) == 0){
                    isUnique = true;
                    break;
                }
            }
        } while (isUnique);
        return uuid;
    }

    public String getNewAccountUUID() {

        String uuid;
        Random rdm = new Random();
        int len = 10;
        boolean nonUnique;

        do{
            uuid = "";
            nonUnique = false;
            for (int i = 0; i < len; i++) {
                uuid += ((Integer)rdm.nextInt(10)).toString();
            }
            for (Account a : this.accounts) {
                if (uuid.compareTo(a.getUUID()) == 0){
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);
        return uuid;
    }

    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    public User addUser(String firstName, String lastName, String pin){
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        Account newAccount = new Account("Savings",newUser,this);

        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    public User userLogin(String userID, String pin){

        for (User u : this.users) {

            if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)){
                return u;
            }
        }
        return null;
    }


    public String getName() {
        return this.name;
    }
}
