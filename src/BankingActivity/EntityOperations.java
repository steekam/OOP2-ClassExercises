/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BankingActivity;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author steekam
 */
public class EntityOperations {
    public static boolean Login(String account, String pin, JFrame frame){
        try {
                Class.forName(DatabaseConnection.DRIVER);
                PreparedStatement prepared_statement;
            //Check user exists using National ID number
            try (Connection connection = DriverManager.getConnection(DatabaseConnection.URL, DatabaseConnection.USER, DatabaseConnection.PASSWORD)) {
                //Check user exists using National ID number
                String query = "SELECT * FROM account WHERE account_number = ?;";
                prepared_statement = connection.prepareStatement(query);
                prepared_statement.setString(1,account);
                ResultSet results = prepared_statement.executeQuery();
                String passwordStored;
                if (results.next()) {
                    passwordStored = results.getString("hashedPin");
                    boolean matched = BCrypt.checkpw(pin, passwordStored);
                    if (!matched) {
                        JOptionPane.showMessageDialog(frame, "Wrong combination of account number and pin. Please try again");
                    }else{
                        frame.dispose();
                        Transactions.account_number = account;
                        new Transactions().setVisible(true);
                        return true;
                    }
                }else{
                    JOptionPane.showMessageDialog(frame, "The account doesn't exist");
                }
            }
                        prepared_statement.close();
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                    }
        return false;
    }
    
    public static void createAccount(String firstName, String lastName, String idNumber, String dob, String pin, JFrame frame){
        try {
                       
                       Class.forName(DatabaseConnection.DRIVER);
            //Check user exists using National ID number
            try (Connection connection = DriverManager.getConnection(DatabaseConnection.URL, DatabaseConnection.USER, DatabaseConnection.PASSWORD)) {
                //Check user exists using National ID number
                String query = "SELECT * FROM customer WHERE id_number = ?;";
                PreparedStatement prepared_statement = connection.prepareStatement(query);
                prepared_statement.setString(1,idNumber);
                ResultSet results = prepared_statement.executeQuery();
                
                if (!results.isBeforeFirst()) {
                    //Adding user to the customer account
                    query = "INSERT INTO customer(first_name,last_name,id_number,DOB) VALUES(?,?,?,?);";
                    prepared_statement = connection.prepareStatement(query);
                    
                    prepared_statement.setString(1, firstName);
                    prepared_statement.setString(2, lastName);
                    prepared_statement.setInt(3, Integer.parseInt(idNumber));
                    prepared_statement.setString(4, dob);
                    prepared_statement.executeUpdate();
                    
                    //
                    //Setting up the account for the user
                    //
                    //Retrieve user ID
                    query = "SELECT customer_id FROM customer WHERE id_number = ?;";
                    prepared_statement = connection.prepareStatement(query);
                    prepared_statement.setInt(1, Integer.parseInt(idNumber));
                    results = prepared_statement.executeQuery();
                    
                    String customer_id = "";
                    while(results.next()){
                        customer_id = results.getString("customer_id");
                    }
                    
                    //Setting up account
                    String hashedPin = BCrypt.hashpw(pin, BCrypt.gensalt(12));
                    query = "INSERT INTO account(customer_id,account_balance,hashedPin) VALUES(?,?,?)";
                    connection.prepareStatement(query);
                    prepared_statement = connection.prepareStatement(query);
                    prepared_statement.setString(1, customer_id);
                    prepared_statement.setInt(2, 0);
                    prepared_statement.setString(3, hashedPin);
                    prepared_statement.executeUpdate();
                    
                    //Retrieve account account number
                    query = "SELECT account_number FROM account WHERE hashedPin = ?;";
                    prepared_statement = connection.prepareStatement(query);
                    prepared_statement.setString(1, hashedPin);
                    results = prepared_statement.executeQuery();
                    
                    String acc_number = "";
                    while(results.next()){
                        acc_number = results.getString("account_number");
                    }
                    
                    JOptionPane.showMessageDialog(frame, "Welcome to the Strata. \n Your account number: "+acc_number);
                    frame.dispose();
                    Dashboard nframe = new Dashboard();
                    nframe.setVisible(true);
                    
                }else{
                    JOptionPane.showMessageDialog(frame, "User already exists");
                }
            }
                       
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AccountCreation.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NumberFormatException exe){
                JOptionPane.showMessageDialog(frame, "Enter valid National ID");
            }
    }
    
    public static void deposit(JFrame frame, int newBalance,String account, String amount){
        try {
            Class.forName(DatabaseConnection.DRIVER);
            try (Connection connection = DriverManager.getConnection(DatabaseConnection.URL, DatabaseConnection.USER, DatabaseConnection.PASSWORD)) {
                String query = "UPDATE account SET account_balance = ? WHERE account_number = ?";
                PreparedStatement prepared_statement = connection.prepareStatement(query);
                prepared_statement.setInt(1,newBalance);
                prepared_statement.setString(2,account);
                prepared_statement.executeUpdate();


                /*
                    Updating the transaction logs
                */
                query = "INSERT INTO transaction_log(account_number,transaction_type,amount) VALUES (?,'deposit',?);";
                prepared_statement = connection.prepareStatement(query);
                prepared_statement.setString(1, account);
                prepared_statement.setInt(2, Integer.parseInt(amount));
                prepared_statement.executeUpdate();

//                                fetchDetails();
//                                textField_amount.setText("");


            }

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage());
        }
    }
    
    public static void withdraw(JFrame frame, int newBalance,String account, String amount){
        try {
                Class.forName(DatabaseConnection.DRIVER);
                try (Connection connection = DriverManager.getConnection(DatabaseConnection.URL, DatabaseConnection.USER, DatabaseConnection.PASSWORD)) {
                    String query = "UPDATE account SET account_balance = ? WHERE account_number = ?";
                    PreparedStatement prepared_statement = connection.prepareStatement(query);
                    prepared_statement.setInt(1,newBalance);
                    prepared_statement.setString(2,account);
                    prepared_statement.executeUpdate();

                     /*
                        Updating the transaction logs
                    */
                    query = "INSERT INTO transaction_log(account_number,transaction_type,amount) VALUES (?,'withdrawal',?);";
                    prepared_statement = connection.prepareStatement(query);
                    prepared_statement.setString(1, account);
                    prepared_statement.setInt(2, Integer.parseInt(amount));
                    prepared_statement.executeUpdate();

                }

                } catch (ClassNotFoundException | SQLException e) {
                    JOptionPane.showMessageDialog(frame, e.getMessage());
                }
    }
}
