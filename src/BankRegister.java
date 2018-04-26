import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BankRegister extends JFrame {     
    Connection connect;
    Statement statement;
    ResultSet resultset;
    
    final String DRIVER,URL,USERNAME,PASSWORD;
    
    
    private JLabel lbl_fName,lbl_lName,lbl_heading,lbl_register;
    private JTextField txtfield_fName,txtfield_lName;
    private JButton btn_register,btn_login;
    private JPanel pnl_layout;
    
    public BankRegister(){
        //Initializing my database credentials
        this.DRIVER = "com.mysql.jdbc.Driver";
        this.URL = "jdbc:mysql://localhost/my_student_bank";
        this.USERNAME = "root";
        this.PASSWORD = "dracarys_";
        
        try {
            //Loading the JBDC Driver
            Class.forName(DRIVER);
            
            //Establishing a connection
            connect = DriverManager.getConnection(URL, USERNAME, PASSWORD);            
            statement =  connect.createStatement();
            
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Could not find classpath for your jbdc driver","Classpath Error",JOptionPane.PLAIN_MESSAGE);
            System.exit(EXIT_ON_CLOSE);
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this, "Could not establish a connection","Connection Error",JOptionPane.PLAIN_MESSAGE);
        }
        
        
        //Setting my frame properties
        setSize(500, 500);
        setTitle("REGISTRATION");
        setLocation(450, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        lbl_heading = new JLabel("WELCOME TO THE STUDENT BANK");
        lbl_fName = new JLabel("First Name:");
        lbl_lName = new JLabel("Last Name:");
        lbl_register = new JLabel("REGISTER");
        
        txtfield_fName = new JTextField(10);
        txtfield_lName = new JTextField(10);
        
        btn_register = new JButton("REGISTER");
        btn_register.addActionListener(new ButtonListener(this));
        btn_login = new JButton("TRANSACT");
        btn_login.addActionListener(new ButtonListener(this));
                
        pnl_layout = new JPanel();
        pnl_layout.setLayout(null);
        pnl_layout.setSize(500, 500);
        
        //positioning the elements on the JPanel
        lbl_heading.setBounds(120, 50, 240, 30);
        lbl_register.setBounds(200, 100, 100, 30);
        lbl_fName.setBounds(50,160,100,30);
        lbl_lName.setBounds(50,200,100,30);
        txtfield_fName.setBounds(200,160,200,30);
        txtfield_lName.setBounds(200,200,200,30);
        btn_register.setBounds(330,270,100,30);
        btn_login.setBounds(200,270,120,30);
        
        
       pnl_layout.add(lbl_heading);
       pnl_layout.add(lbl_register);
       pnl_layout.add(lbl_fName);
       pnl_layout.add(lbl_lName);
       pnl_layout.add(txtfield_fName);
       pnl_layout.add(txtfield_lName);
       pnl_layout.add(btn_register);
       pnl_layout.add(btn_login);
       
       add(pnl_layout);
        
        
    }
    
    class ButtonListener implements ActionListener{
        JFrame frame;
        public ButtonListener(JFrame frame){
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btn_login) {
                frame.dispose();
                new BankLogin().setVisible(true);
            } else if(e.getSource() == btn_register){
                try {
                    if (txtfield_fName.getText().isEmpty() || txtfield_lName.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Please fill in all textfields","EMPTY FIELDS",JOptionPane.PLAIN_MESSAGE);
                    }else{
                        String fName = txtfield_fName.getText();
                        String lName = txtfield_lName.getText();
                        String accountNumber = Integer.toString(Generator.generate());
                        
//                        Adding the details to the database

                        Banking temp_user = new Banking();

                        //Adding the new user
                        //Banking.store.put(accountNumber, new Banking(fName,lName,accountNumber));

                        temp_user = Banking.store.get(accountNumber);
                        JOptionPane.showMessageDialog(frame, "Welcome, "+temp_user.getfName()+". \nYour account number is "+temp_user.getAccountNumber(),"WELCOME",JOptionPane.PLAIN_MESSAGE);

                        frame.setVisible(false);
                        new BankLogin().setVisible(true);
                    }
                    
                } catch (HeadlessException exe) {
                    
                }
            }
        }
    
    }    
    
    
    public static void main(String[] args) {
        new BankRegister().setVisible(true);
    }
}
