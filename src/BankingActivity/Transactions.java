
package BankingActivity;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.jdesktop.swingx.JXDatePicker;


public class Transactions extends JFrame{
    private JPanel pnl_side,pnl_topUser,pnl_bottomMenu,pnl_transactions,pnl_display,pnl_account,pnl_cardLayout,
            pnl_balance,pnl_BottomTransact;
    private JPanel pnl_withdraw,pnl_deposit,pnl_accountDetails,pnl_enterAmount;
    private JLabel lbl_transactions,lbl_accNum,lbl_userIcon,lbl_deposit,lbl_withdraw,lbl_accountDetails,lbl_enterAmount,lbl_balance;
    private JTextField textField_accNum,textField_amount,textField_balance;
    private JButton btn_deposit,btn_withdraw,btn_logout;
    private JSeparator separator_enterAmount;
    
    static String account_number = "";    
    
    
    CardLayout cl = new CardLayout(); 
    
    public Transactions(){
        super("THE STRATA | TRANSACTIONS");
        setSize(700,500);
        setLocation(320,90);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        initComponents();        
        
        textField_accNum.setText(account_number);
        fetchDetails();
        setVisible(true);
    }
    
    private void initComponents(){
        setUpSidePanel();        
        add(pnl_side);
        setUpCardLayoutPanel();
        add(pnl_cardLayout); 
        setUpAccountDetails();
        
    }  
    
    private void setUpSidePanel(){
        //Setting up the side panel for the menu
        Color topPanelColor = new Color(12, 102, 130);
        pnl_side = new JPanel();
        pnl_side.setLayout(null);
        pnl_side.setBackground(new Color(66, 135, 130));
        pnl_side.setBounds(0,0,250,500);
        
        //Top user panel with user icon
        pnl_topUser = new JPanel();
        pnl_topUser.setLayout(null);
        pnl_topUser.setBackground(topPanelColor);
        pnl_topUser.setBounds(0,0,250,140);
        
        ImageIcon icon_user = new ImageIcon("images/user.png");
        lbl_userIcon = new JLabel(icon_user);
        lbl_userIcon.setBounds(70,10,96,96);
        pnl_topUser.add(lbl_userIcon);
        
        lbl_accNum = new JLabel("ACCOUNT #:");
        propertyLabels(lbl_accNum);
        lbl_accNum.setBounds(15,106,80,30);
        pnl_topUser.add(lbl_accNum);
        
        textField_accNum = new JTextField();
        textField_accNum.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        textField_accNum.setForeground(Color.white);
        textField_accNum.setBackground(topPanelColor);
        textField_accNum.setFont(new Font("Sans serif", Font.PLAIN, 12));
        textField_accNum.setEditable(false);
        textField_accNum.setBounds(95,106,100,30);
        pnl_topUser.add(textField_accNum);
        
        pnl_side.add(pnl_topUser);
        
        //Setting up bottom panel with menus
        pnl_bottomMenu = new JPanel();
        pnl_bottomMenu.setLayout(null);
        Color bottomPanelColor = new Color(66, 135, 130);
        pnl_bottomMenu.setBackground(bottomPanelColor);
        pnl_bottomMenu.setBounds(0,140,250,360);
        pnl_side.add(pnl_bottomMenu);
        
        pnl_accountDetails = new JPanel();
        pnl_accountDetails.setLayout(null);
        lbl_accountDetails = new JLabel("CUSTOMER DETAILS");
        propertyLabels(lbl_accountDetails);
        propertySideMenuItems(pnl_accountDetails, lbl_accountDetails, 250, 40, 0, 0);        
        pnl_bottomMenu.add(pnl_accountDetails);
        
        pnl_transactions = new JPanel();
        pnl_transactions.setLayout(null);
        lbl_transactions = new JLabel("TRANSACTIONS ...");
        propertyLabels(lbl_transactions);      
        propertySideMenuItems(pnl_transactions, lbl_transactions, 250, 40, 0, 40);
        pnl_transactions.setBackground(new Color(91, 178, 172));
        pnl_bottomMenu.add(pnl_transactions);
        
        pnl_deposit = new JPanel();
        pnl_deposit.setLayout(null);
        lbl_deposit = new JLabel("DEPOSIT");
        propertyLabels(lbl_deposit);
        propertySideMenuItems(pnl_deposit, lbl_deposit, 250, 40, 0, 80);
        pnl_deposit.setBackground(new Color(53, 99, 96));
        pnl_bottomMenu.add(pnl_deposit);
        
        pnl_withdraw = new JPanel();
        pnl_withdraw.setLayout(null);
        lbl_withdraw = new JLabel("WITHDRAW");
        propertyLabels(lbl_withdraw);
        pnl_withdraw.setBackground(new Color(91, 178, 172));
        propertySideMenuItems(pnl_withdraw, lbl_withdraw, 250, 40, 0, 120);        
        pnl_bottomMenu.add(pnl_withdraw);
        
        btn_logout = new JButton("LOGOUT");
        btn_logout.setForeground(Color.white);
        btn_logout.setBackground(new Color(46, 73, 138));
        btn_logout.setBounds(60,280,100,30);
        pnl_bottomMenu.add(btn_logout);
        
        /*
            User logout
        */
        btn_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Transactions.this.dispose();
                Dashboard frame = new Dashboard();
                frame.setVisible(true);
            }
        });
        
        //User friendlyness
        menuItemClicked(pnl_accountDetails, pnl_transactions, pnl_deposit, pnl_withdraw);
        subMenuTransactionsClicked(pnl_deposit,pnl_accountDetails, pnl_transactions,  pnl_withdraw);
        menuItemClicked(pnl_transactions, pnl_deposit, pnl_accountDetails, pnl_withdraw);
        subMenuTransactionsClicked(pnl_withdraw,pnl_accountDetails, pnl_transactions, pnl_deposit);
        
    }
    
    private void setUpCardLayoutPanel(){
        pnl_cardLayout = new JPanel(cl); 
        pnl_cardLayout.setBounds(250,0,450,500);
        pnl_cardLayout.setBackground(Color.red);
        
        
        //Panel for account details
        pnl_account = new JPanel();
        pnl_account.setLayout(null);
        pnl_account.setBackground(Color.white);
        pnl_cardLayout.add(pnl_account,"card_account");        
        
        //Panel display for the transactions 
        pnl_display = new JPanel();
        pnl_display.setLayout(null);
        pnl_display.setBackground(Color.white);
        pnl_cardLayout.add(pnl_display,"card_display");
        cl.show(pnl_cardLayout, "card_display");
        
                //Showing the display panel
            pnl_transactions.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    pnl_deposit.setBackground(new Color(53, 99, 96));
                    btn_deposit.setVisible(true);
                    btn_withdraw.setVisible(false);
                    cl.show(pnl_cardLayout, "card_display");
                }
                
            });
                //Showing account details when menu is clicked
            pnl_accountDetails.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    cl.show(pnl_cardLayout,"card_account");
                    showAccountDetails();
                }
                
            });
            
                /*
                    Top panel for showing balance
                */
        pnl_balance = new JPanel();
        pnl_balance.setBackground(new Color(13, 121, 155));
        pnl_balance.setBounds(0,0,450,140);
        pnl_balance.setLayout(null);
        pnl_display.add(pnl_balance);
            //the components in balance panel
            lbl_balance = new JLabel("CURRENT BALANCE:");
            lbl_balance.setFont(new Font(Font.MONOSPACED,Font.PLAIN,25));
            lbl_balance.setForeground(Color.white);
            lbl_balance.setBounds(90,20,250,50);
            pnl_balance.add(lbl_balance);
            textField_balance =  new JTextField("250");
            textField_balance.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            textField_balance.setForeground(Color.white);
            textField_balance.setBackground(new Color(13, 121, 155));
            textField_balance.setFont(new Font(Font.MONOSPACED,Font.PLAIN,25));
            textField_balance.setBounds(160,65,200,40);
            pnl_balance.add(textField_balance);
        
            /*
                Bottom panel where the transactions are
            */
            pnl_BottomTransact = new JPanel();
            pnl_BottomTransact.setLayout(null);
            pnl_BottomTransact.setBounds(0,140,450,360);
            pnl_BottomTransact.setBackground(Color.white);
            pnl_display.add(pnl_BottomTransact);
            
                //Components for the bottom transact panel
            pnl_enterAmount = new JPanel();
            pnl_enterAmount.setLayout(null);
            pnl_enterAmount.setBounds(20,55,300,200);
            
            JPanel side_color = new JPanel();
            side_color.setBackground(new Color(13, 121, 155));
            side_color.setBounds(0,0,300,40);
            pnl_enterAmount.add(side_color);
            
            pnl_BottomTransact.add(pnl_enterAmount);
            lbl_enterAmount = new JLabel("ENTER AMOUNT");
            lbl_enterAmount.setFont(new Font(Font.MONOSPACED,Font.PLAIN,18));
            lbl_enterAmount.setBounds(20,45,150,40);
            pnl_enterAmount.add(lbl_enterAmount);
            
            textField_amount = new JTextField();
            textField_amount.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            textField_amount.setFont(new Font(Font.MONOSPACED,Font.PLAIN,18));
            textField_amount.setBounds(20, 85, 150, 40);
            pnl_enterAmount.add(textField_amount);
            
            separator_enterAmount = new JSeparator();
            separator_enterAmount.setBackground(new Color(13, 121, 155));
            separator_enterAmount.setBounds(20, 125, 150, 10);
            pnl_enterAmount.add(separator_enterAmount);
            
            //Buttons for deposit and withdraw
            btn_deposit = new JButton("DEPOSIT");
            btn_withdraw = new JButton("WITHDRAW");
            btn_deposit.setForeground(Color.white);
            btn_withdraw.setForeground(Color.white);
            btn_deposit.setBackground(new Color(46, 73, 138));
            btn_withdraw.setBackground(new Color(46, 73, 138));
            btn_deposit.setBounds(20,145,150, 40);
            btn_withdraw.setBounds(20,145,150, 40);
            pnl_enterAmount.add(btn_deposit);
            pnl_enterAmount.add(btn_withdraw);
            btn_withdraw.setVisible(false);

            //Toggling between deposit and withdraw
            pnl_deposit.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    cl.show(pnl_cardLayout, "card_display");
                    btn_deposit.setVisible(true);
                    btn_withdraw.setVisible(false);
                }
                
            });
            
            pnl_withdraw.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    cl.show(pnl_cardLayout, "card_display");
                    btn_deposit.setVisible(false);
                    btn_withdraw.setVisible(true);
                }
                    
            });
            
            /*
                Dealing with the transactions of deposit and withdraw
            */
            btn_deposit.addActionListener((ActionEvent e) -> {
                deposit();
            });
            
            btn_withdraw.addActionListener((ActionEvent e) -> {
                withdraw();
            });
        
    }
    
    /*
            Components required for showing the details
    */
        JTextField textField_fname,textField_lname,textField_idnumber,textField_dob;
        JLabel lbl_fname,lbl_lname,lbl_dob,lbl_idnumber;
        JSeparator separator_fname,separator_lname,separator_idnumber,separator_dob;
        JButton btn_changePin,btn_editDetails,btn_submitChanges;
        
    private void setUpAccountDetails(){
        JPanel wrapper = new JPanel();
        wrapper.setLayout(null);
        wrapper.setBackground(Color.white);
        wrapper.setBounds(0,20,430,340);
        pnl_account.add(wrapper);
        
        
        lbl_fname = new JLabel("FIRST NAME");
        propertyLabelsAccountDetails(lbl_fname);
        lbl_fname.setBounds(15,0,150,30);
        wrapper.add(lbl_fname);
        textField_fname = new JTextField();
        propertyTextField(textField_fname);
        textField_fname.setBounds(15,25,200,30);
        wrapper.add(textField_fname);
        separator_fname = new JSeparator();
        separator_fname.setBackground(new Color(66, 135, 130));
        separator_fname.setBounds(15,55,200,10);
        wrapper.add(separator_fname);
        
        lbl_lname = new JLabel("LAST NAME");
        propertyLabelsAccountDetails(lbl_lname);
        lbl_lname.setBounds(15,70,150,30);
        wrapper.add(lbl_lname);
        textField_lname = new JTextField();
        propertyTextField(textField_lname);
        textField_lname.setBounds(15,100,200,30);
        wrapper.add(textField_lname);
        separator_lname = new JSeparator();
        separator_lname.setBackground(new Color(66, 135, 130));
        separator_lname.setBounds(15,130,200,10);
        wrapper.add(separator_lname);
        
        lbl_idnumber = new JLabel("NATIONAL ID");
        propertyLabelsAccountDetails(lbl_idnumber);
        lbl_idnumber.setBounds(15,145,150,30);
        wrapper.add(lbl_idnumber);
        textField_idnumber = new JTextField();
        propertyTextField(textField_idnumber);
        textField_idnumber.setBounds(15,175,200,30);
        wrapper.add(textField_idnumber);
        separator_idnumber = new JSeparator();
        separator_idnumber.setBackground(new Color(66, 135, 130));
        separator_idnumber.setBounds(15,205,200,10);
        wrapper.add(separator_idnumber);
        
        lbl_dob = new JLabel("Date of Birth");
        propertyLabelsAccountDetails(lbl_dob);
        lbl_dob.setBounds(15,220,150,30);
        wrapper.add(lbl_dob); 
        textField_dob = new JTextField();
        propertyTextField(textField_dob);
        textField_dob.setBounds(15,250,200,30);
        wrapper.add(textField_dob);
        separator_dob = new JSeparator();
        separator_dob.setBackground(new Color(66, 135, 130));
        separator_dob.setBounds(15,280,200,10);
        wrapper.add(separator_dob);
        
        //when editing the Date of Birth
        JXDatePicker jxdatepicker = new JXDatePicker();             
        jxdatepicker.setBounds(15, 250, 200, 30);
        jxdatepicker.setFormats("yyyy-MM-dd");
        wrapper.add(jxdatepicker);
        jxdatepicker.setVisible(false);
        
        btn_changePin = new JButton("RESET PIN");
        btn_changePin.setForeground(Color.white);
        btn_changePin.setBackground(new Color(251, 204, 0));
        btn_changePin.setBounds(15,300,200,30);
        wrapper.add(btn_changePin);
        
        btn_editDetails = new JButton("EDIT");
        btn_editDetails.setForeground(Color.white);
        btn_editDetails.setBackground(new Color(251, 204, 0));
        btn_editDetails.setBounds(300,0,80,30);
        wrapper.add(btn_editDetails);
        
        btn_submitChanges = new JButton("SAVE CHANGES");
        btn_submitChanges.setForeground(Color.white);
        btn_submitChanges.setBackground(new Color(0, 179, 66));
        btn_submitChanges.setBounds(230,0,150,30);
        wrapper.add(btn_submitChanges);
        btn_submitChanges.setVisible(false);
        
        /*
            Toggling the editing of the account details
        */
        btn_editDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btn_editDetails.setVisible(false);
                btn_submitChanges.setVisible(true);
                
                setEditable(textField_fname,textField_lname,textField_dob,textField_idnumber);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    if (!textField_dob.getText().isEmpty()) {
                        Date date1 = df.parse(textField_dob.getText());
                        jxdatepicker.setDate(date1);
                    }

                } catch (ParseException ex) {
                    Logger.getLogger(Transactions.class.getName()).log(Level.SEVERE, null, ex);
                }   
                jxdatepicker.setVisible(true);
                textField_dob.setVisible(false);
            }
        });
        
        btn_submitChanges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editAccountDetails();
                btn_editDetails.setVisible(true);
                btn_submitChanges.setVisible(false);
                textField_dob.setVisible(true);
                jxdatepicker.setVisible(false);
                setUnEditable(textField_fname,textField_lname,textField_dob,textField_idnumber);
            }
        });
        
    }
    
    private void fetchDetails(){
        /*
            Fetching details from the database about the account
            */
        try {
            
            Class.forName(DatabaseConnection.DRIVER);
            PreparedStatement prepared_statement;
            try (Connection connection = DriverManager.getConnection(DatabaseConnection.URL, DatabaseConnection.USER, DatabaseConnection.PASSWORD)) {
                String query = "SELECT * FROM account WHERE account_number = ?";
                prepared_statement = connection.prepareStatement(query);
                prepared_statement.setString(1,textField_accNum.getText());
                ResultSet results = prepared_statement.executeQuery();
                int balance = 0;
                while(results.next()){
                    balance = results.getInt("account_balance");
                }   textField_balance.setText(Integer.toString(balance));
            }
            prepared_statement.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Transactions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void deposit(){
    /*
        Function to make a deposit to the account 
    */      
        if (textField_amount.getText().isEmpty()) {
            JOptionPane.showMessageDialog(Transactions.this, "Please enter an amount");
        }
            int currentBalance = Integer.parseInt(textField_balance.getText());
            int newBalance = currentBalance + Integer.parseInt(textField_amount.getText());
            
        try {
            Class.forName(DatabaseConnection.DRIVER);
            try (Connection connection = DriverManager.getConnection(DatabaseConnection.URL, DatabaseConnection.USER, DatabaseConnection.PASSWORD)) {
                String query = "UPDATE account SET account_balance = ? WHERE account_number = ?";
                PreparedStatement prepared_statement = connection.prepareStatement(query);
                prepared_statement.setInt(1,newBalance);
                prepared_statement.setString(2,textField_accNum.getText());
                prepared_statement.executeUpdate();
                
                
                /*
                    Updating the transaction logs
                */
                query = "INSERT INTO transaction_log(account_number,transaction_type,amount) VALUES (?,'deposit',?);";
                prepared_statement = connection.prepareStatement(query);
                prepared_statement.setString(1, textField_accNum.getText());
                prepared_statement.setInt(2, Integer.parseInt(textField_amount.getText()));
                prepared_statement.executeUpdate();
                
                fetchDetails();
                textField_amount.setText("");
                
                
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        
    }
    
    private void withdraw(){
        /*
            Function to help in making a withdrawal
        */
        if (textField_amount.getText().isEmpty()) {
            JOptionPane.showMessageDialog(Transactions.this, "Please enter an amount");
            textField_amount.setText("");
        }
        
        int currentBalance = Integer.parseInt(textField_balance.getText());
        if (currentBalance < Integer.parseInt(textField_amount.getText())) {
            JOptionPane.showMessageDialog(this, "Dear customer, you have insufficient funds");
        }else{
            int newBalance = currentBalance - Integer.parseInt(textField_amount.getText());
            
            try {
            Class.forName(DatabaseConnection.DRIVER);
            try (Connection connection = DriverManager.getConnection(DatabaseConnection.URL, DatabaseConnection.USER, DatabaseConnection.PASSWORD)) {
                String query = "UPDATE account SET account_balance = ? WHERE account_number = ?";
                PreparedStatement prepared_statement = connection.prepareStatement(query);
                prepared_statement.setInt(1,newBalance);
                prepared_statement.setString(2,textField_accNum.getText());
                prepared_statement.executeUpdate();
                
                 /*
                    Updating the transaction logs
                */
                query = "INSERT INTO transaction_log(account_number,transaction_type,amount) VALUES (?,'withdrawal',?);";
                prepared_statement = connection.prepareStatement(query);
                prepared_statement.setString(1, textField_accNum.getText());
                prepared_statement.setInt(2, Integer.parseInt(textField_amount.getText()));
                prepared_statement.executeUpdate();
                
                fetchDetails();
                textField_amount.setText("");
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        }
        
    }
    
    private void editAccountDetails(){
        /*
            Changes are saved to the relevant user
        */
        
        
        
    }
    
    private void showAccountDetails(){
        try {
            
            Class.forName(DatabaseConnection.DRIVER);
            PreparedStatement prepared_statement;
            try (Connection connection = DriverManager.getConnection(DatabaseConnection.URL, DatabaseConnection.USER, DatabaseConnection.PASSWORD)) {
                String query = "SELECT * FROM customer INNER JOIN account ON customer.customer_id = account.customer_id WHERE account.account_number = ?";
                prepared_statement = connection.prepareStatement(query);
                prepared_statement.setString(1,textField_accNum.getText());
                ResultSet results = prepared_statement.executeQuery();
                
                while(results.next()){
                    textField_fname.setText(results.getString("first_name"));
                    textField_lname.setText(results.getString("last_name"));
                    textField_idnumber.setText(results.getString("id_number"));
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    textField_dob.setText(df.format(results.getObject("DOB")));
                }   
            }
            prepared_statement.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Transactions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setEditable(JTextField field1, JTextField field2, JTextField field3, JTextField field4){
        field1.setEditable(true);
        field2.setEditable(true);
        field3.setEditable(true);
        field4.setEditable(true);
        
        field1.setBackground(Color.LIGHT_GRAY);
        field2.setBackground(Color.LIGHT_GRAY);
        field3.setBackground(Color.LIGHT_GRAY);
        field4.setBackground(Color.LIGHT_GRAY);
    }
    
    private void setUnEditable(JTextField field1, JTextField field2, JTextField field3, JTextField field4){
        field1.setEditable(false);
        field2.setEditable(false);
        field3.setEditable(false);
        field4.setEditable(false);
        
        field1.setBackground(Color.white);
        field2.setBackground(Color.white);
        field3.setBackground(Color.white);
        field4.setBackground(Color.white);
    }
    /*    
            Utilities to help in the stylings
    */
    //Function for label properties
    private void propertyLabels(JLabel label){
        label.setFont(new Font("Sans serif",Font.PLAIN,12));
        label.setForeground(Color.WHITE);
    }
    
     private void propertySideMenuItems(JPanel panel, JLabel label, int width, int height,int x , int y){
         
         Color PanelUnclickedColor = new Color(66, 135, 130);
         panel.setBackground(PanelUnclickedColor);
         panel.setBounds(x,y,width,height);
         
         label.setBounds(40,0,150,40);
         panel.add(label);       
    
    }
     
    private void menuItemClicked(JPanel panel1,JPanel panel2,JPanel panel3,JPanel panel4){
        panel1.addMouseListener(new MouseAdapter() {
         Color PanelUnclickedColor = new Color(66, 135, 130);
         Color PanelClickedColor = new Color (91, 178, 172);
            @Override
            public void mouseClicked(MouseEvent e) {
                panel1.setBackground(PanelClickedColor);
                panel2.setBackground(PanelUnclickedColor);
                panel3.setBackground(PanelUnclickedColor);
                panel4.setBackground(PanelUnclickedColor);                    
                
            }
           
        });
    }
    
    private void subMenuTransactionsClicked(JPanel panel1,JPanel panel2,JPanel panel3,JPanel panel4){
        panel1.addMouseListener(new MouseAdapter() {
         Color PanelUnclickedColor = new Color(66, 135, 130);
         Color PanelClickedColor = new Color (91, 178, 172);
         Color subMenuClickedColor = new Color(53, 99, 96);
            @Override
            public void mouseClicked(MouseEvent e) {
                panel1.setBackground(subMenuClickedColor);
                panel2.setBackground(PanelUnclickedColor);
                panel3.setBackground(PanelClickedColor);
                panel4.setBackground(PanelUnclickedColor);                    
                
            }
           
        });
    }
    
    //Function to set property for the similar input fields
    private void propertyTextField(JTextField input){
        input.setBorder(javax.swing.BorderFactory.createEmptyBorder());        
        input.setFont(new Font("Sans serif", Font.PLAIN, 11));
        input.setBackground(Color.WHITE);
        input.setEditable(false);
    }
    
    //Function for label properties for account details
    private void propertyLabelsAccountDetails(JLabel label){
        label.setFont(new Font("Sans serif",Font.PLAIN,12));
        label.setForeground(new Color(66, 135, 130));
    }
    
    
//    
//    public static void main(String[] args) {
//        new Transactions().setVisible(true);
//    }
}
