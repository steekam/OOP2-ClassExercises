
package BankingActivity;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import javax.swing.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.swingx.*;


public class AccountCreation extends JFrame{
    private JTextField textField_fname,textField_lname,textField_idnumber;
    private JPasswordField pin;
    private JLabel lbl_fname,lbl_lname,lbl_dob,lbl_idnumber,lbl_accountNumber,lbl_pin,lbl_icon;
    private JPanel pnl_right, pnl_left;
    private JButton btn_create,btn_login;
    private JSeparator separator_fname,separator_lname,separator_idnumber,separator_pin1;
    private JXDatePicker jxdatepicker;
    
    public AccountCreation(){
        super("STRATA BANK | ACCOUNT CREATION");
        setSize(700,500);
        setLocation(320,90);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);        
        
        initComponents();
        addDetails();
        btn_loginClicked();
        setVisible(true);
 
    }
    
    private void initComponents(){
        pnl_left = new JPanel();
        pnl_left.setLayout(null);
        pnl_right = new JPanel();
        pnl_right.setLayout(null);
        
        //Font and color for my labels        
        Color color_white = new Color(255,255,255);        
        
        //Setting up the left panel        
        lbl_accountNumber = new JLabel("PLEASE PROVIDE THE INFORMATION BELOW");        
        propertyLabels(lbl_accountNumber);
        lbl_accountNumber.setBounds(15,30,300,30);
        pnl_left.add(lbl_accountNumber);        

        
        lbl_fname = new JLabel("FIRST NAME");
        propertyLabels(lbl_fname);
        lbl_fname.setBounds(15,80,150,30);
        pnl_left.add(lbl_fname);
        textField_fname = new JTextField();
        propertyTextField(textField_fname);
        textField_fname.setBounds(15,105,200,30);
        pnl_left.add(textField_fname);
        separator_fname = new JSeparator();
        separator_fname.setBackground(Color.white);
        separator_fname.setBounds(15,135,200,10);
        pnl_left.add(separator_fname);
        
        lbl_lname = new JLabel("LAST NAME");
        propertyLabels(lbl_lname);
        lbl_lname.setBounds(15,145,150,30);
        pnl_left.add(lbl_lname);
        textField_lname = new JTextField();
        propertyTextField(textField_lname);
        textField_lname.setBounds(15,170,200,30);
        pnl_left.add(textField_lname);
        separator_lname = new JSeparator();
        separator_lname.setBackground(Color.white);
        separator_lname.setBounds(15,200,200,10);
        pnl_left.add(separator_lname);
        
        lbl_idnumber = new JLabel("NATIONAL ID");
        propertyLabels(lbl_idnumber);
        lbl_idnumber.setBounds(15,210,150,30);
        pnl_left.add(lbl_idnumber);
        textField_idnumber = new JTextField();
        propertyTextField(textField_idnumber);
        textField_idnumber.setBounds(15,235,200,30);
        pnl_left.add(textField_idnumber);
        separator_idnumber = new JSeparator();
        separator_idnumber.setBackground(Color.white);
        separator_idnumber.setBounds(15,265,200,10);
        pnl_left.add(separator_idnumber);
        
        lbl_dob = new JLabel("Date of Birth");
        propertyLabels(lbl_dob);
        lbl_dob.setBounds(15,275,150,30);
        pnl_left.add(lbl_dob);        
        jxdatepicker = new JXDatePicker(new Date());        
        jxdatepicker.setBounds(15, 305, 200, 30);
        jxdatepicker.setFormats("yyyy-MM-dd");
        pnl_left.add(jxdatepicker);
        
        lbl_pin = new JLabel("ACCOUNT PIN");
        propertyLabels(lbl_pin);
        lbl_pin.setBounds(15,335,100,30);
        pnl_left.add(lbl_pin);       
        
        //Setting maximum character to be four
        MaxLengthTextDocument maxLength = new MaxLengthTextDocument();
	maxLength.setMaxChars(5);//4 is the maximum number of character
        
        pin = new JPasswordField();
        propertyPasswordField(pin);
        pin.setDocument(maxLength);
        pin.setBounds(15,360,100,30);
        pnl_left.add(pin);
        separator_pin1 = new JSeparator();
        separator_pin1.setBackground(Color.white);
        separator_pin1.setBounds(15,390,100,10);
        pnl_left.add(separator_pin1);
        
        btn_create = new JButton("CREATE ACCOUNT");
        btn_create.setForeground(color_white);
        btn_create.setBackground(new Color(46, 73, 138));
        btn_create.setBounds(15,400,200,40);        
        pnl_left.add(btn_create);
        
        
        /*
            SETTING UP RIGHT PANEL
        */
        lbl_icon = new JLabel();
        ImageIcon icon_account = new ImageIcon("images/safe96.png");        
        lbl_icon.setIcon(icon_account);
        lbl_icon.setBounds(150,150,80,80);
        pnl_right.add(lbl_icon);
        
        btn_login = new JButton("LOGIN");
        btn_login.setForeground(Color.white);
        btn_login.setFont(new Font("Sans Serif",Font.BOLD,12));
        btn_login.setBackground(new Color(46, 73, 138));
        btn_login.setFocusPainted(false);
        btn_login.setBounds(230,10,100,30);
        pnl_right.add(btn_login);
        
        
        //Adding the panels to the JFrame
        pnl_left.setBackground(new Color(66, 135, 130));
        pnl_right.setBackground(new Color(17, 79, 89));
        pnl_left.setBounds(0, 0, 350, 500);
        pnl_right.setBounds(350, 0, 350, 500);
        add(pnl_left);
        add(pnl_right);     
       
    }
    
    //Event Handling
        /*
            Adding the details to the database
        */
    private void addDetails(){
         btn_create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Check all fields are filled
                
               Date dob = jxdatepicker.getDate();
               String dobString = new SimpleDateFormat("yyyy-MM-dd").format(dob);
               
                if (textField_fname.getText().isEmpty()
                        ||textField_idnumber.getText().isEmpty()
                        ||textField_lname.getText().isEmpty() || String.valueOf(pin.getPassword()).isEmpty()) {
                    JOptionPane.showMessageDialog(AccountCreation.this, "Please fill in all fields");                    
                }else if (!isAbove18(dob)) {
                    JOptionPane.showMessageDialog(AccountCreation.this, "Account holders must be above 18 years");
                }else {
                    //
                    //
                    //Add user to database
                    //
                    //
                   try {
                       
                       Class.forName(DatabaseConnection.DRIVER);
                       Connection connection = DriverManager.getConnection(DatabaseConnection.URL, DatabaseConnection.USER, DatabaseConnection.PASSWORD);
                       //Check user exists using National ID number
                       String query = "SELECT * FROM customer WHERE id_number = ?;";
                       PreparedStatement prepared_statement = connection.prepareStatement(query);
                       prepared_statement.setString(1,textField_idnumber.getText());
                       ResultSet results = prepared_statement.executeQuery();
                       
                       if (!results.isBeforeFirst()) {
                           //Adding user to the customer account
                            query = "INSERT INTO customer(first_name,last_name,id_number,DOB) VALUES(?,?,?,?);";
                            prepared_statement = connection.prepareStatement(query);

                            prepared_statement.setString(1, textField_fname.getText());
                            prepared_statement.setString(2, textField_lname.getText());
                            prepared_statement.setString(3, textField_idnumber.getText());
                            prepared_statement.setString(4, dobString);
                            prepared_statement.executeUpdate();
                            
                            //
                            //Setting up the account for the user
                            //
                                //Retrieve user ID
                            query = "SELECT customer_id FROM customer WHERE id_number = ?;";
                            prepared_statement = connection.prepareStatement(query);
                            prepared_statement.setInt(1, Integer.parseInt(textField_idnumber.getText()));
                            results = prepared_statement.executeQuery();
                            
                            String customer_id = "";
                            while(results.next()){
                                customer_id = results.getString("customer_id");
                            }
                            
                            //Setting up account
                            String hashedPin = BCrypt.hashpw(String.valueOf(pin.getPassword()), BCrypt.gensalt(12));                            
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
                            
                            JOptionPane.showMessageDialog(AccountCreation.this, "Welcome to the Strata. \n Your account number: "+acc_number);
                            AccountCreation.this.dispose();
                            Dashboard frame = new Dashboard();
                            frame.setVisible(true);
                                
                       }else{
                           JOptionPane.showMessageDialog(AccountCreation.this, "User already exists");
                       }                       
                       
                       connection.close();
                       
                   } catch (ClassNotFoundException | SQLException ex) {
                       Logger.getLogger(AccountCreation.class.getName()).log(Level.SEVERE, null, ex);
                   }
                    
                }
            }
        });
    }
    
    private void btn_loginClicked(){
        btn_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccountCreation.this.dispose();
                Dashboard frame = new Dashboard();
                frame.setVisible(true);
            }
        });
    }
    
    //Function for label properties
    private void propertyLabels(JLabel label){
        label.setFont(new Font("Sans serif",Font.PLAIN,12));
        label.setForeground(Color.WHITE);
    }
    
    //Function to set property for the similar input fields
    private void propertyTextField(JTextField input){
        input.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        input.setForeground(Color.white);
        input.setBackground(new Color(66, 135, 130));
        input.setFont(new Font("Sans serif", Font.PLAIN, 11));
    }
    
    private void propertyPasswordField(JPasswordField input){
        input.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        input.setForeground(Color.white);
        input.setBackground(new Color(66, 135, 130));
        input.setFont(new Font("Sans serif", Font.PLAIN, 11));        
    }
    
    //Function to check if user is 18 and above
    private Boolean isAbove18(Date date){
        LocalDate today = LocalDate.now();
        LocalDate dob = convertToLocalDateViaInstant(date);
        long years = dob.until(today, ChronoUnit.YEARS);
        return years >= 18;
    }
    
    //Converts Date to LocalDate
    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate();
    }

    
//    public static void main(String[] args) {
//       AccountCreation frame = new AccountCreation();
//       frame.setVisible(true);
//    }
}
