
import java.util.HashMap;

public class Banking {
    
    //Fields for eah account holder
    private String fName,lName;
    private String accountNumber,hashedPin;
    private double balance;
    
    static HashMap<String,Banking> store = new HashMap<>();
    
        
    public Banking(){
        
    }

    public Banking(String fName, String lName, String accountNumber,String hashedPin) {
        this.fName = fName;
        this.lName = lName;
        this.accountNumber = accountNumber;
        this.hashedPin = hashedPin;
    }

    public String getfName() {
        return fName;
    }

    public String getHashedPin() {
        return hashedPin;
    }

    public void setHashedPin(String hashedPin) {
        this.hashedPin = hashedPin;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }    
    
    
    //Making a withdrawal
    public int withdraw(double amount){                
        if (amount > balance) {
            return 0;
        }else{
           balance = balance - amount;           
        } 
        return 1;
    }
    
    //Making a deposit
    public void deposit(double amount){
        balance = balance + amount;
        System.out.println("Deposited: " + amount);
        System.out.println("New balance: " + balance);
    }
    
    //Status check
    public void status(){    
        System.out.println("Account: " + accountNumber);
        System.out.println("Balance: " + balance);
    }
    
    public static HashMap getStore (){
        return store;
    }
}
