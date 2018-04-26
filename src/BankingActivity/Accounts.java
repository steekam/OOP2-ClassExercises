
package BankingActivity;


public class Accounts {
    private String fName,lName,IDnumber,Dob,hashedPin,account_number;

    public Accounts(String fName, String lName, String IDnumber, String Dob, String account_number) {
        this.fName = fName;
        this.lName = lName;
        this.IDnumber = IDnumber;
        this.Dob = Dob;
        this.account_number = account_number;
    }

    public String getfName() {
        return fName;
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

    public String getIDnumber() {
        return IDnumber;
    }

    public void setIDnumber(String IDnumber) {
        this.IDnumber = IDnumber;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String Dob) {
        this.Dob = Dob;
    }

    public String getHashedPin() {
        return hashedPin;
    }

    public void setHashedPin(String hashedPin) {
        this.hashedPin = hashedPin;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }
    
    
    
    
}
