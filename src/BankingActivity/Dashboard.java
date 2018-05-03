
package BankingActivity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Dashboard extends JFrame{
    private JPanel side_left,side_right;
    private JLabel lbl_title,lbl_description,lbl_luring,lbl_accountnumber,lbl_pin,lbl_icon;
    private JTextField textField_accountnumber;
    private JPasswordField password_pin;
    private JButton btn_login,btn_register;
    
    public Dashboard(){
        super("STRATA BANK");
        setSize(700,500);
        setResizable(false);
        setLocation(320,90);
        setLayout(null);        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        
        initComponents();
        setHandling();
        setVisible(true);
    }
    
    
    private void initComponents(){
        lbl_title = new JLabel("THE STRATA");
        lbl_description = new JLabel("The most secure bank on the island");
        lbl_luring = new JLabel("Secure your resources with us");
        lbl_icon = new JLabel();
        lbl_accountnumber = new JLabel("Account number");
        lbl_pin = new JLabel("PIN");
        
        textField_accountnumber = new JTextField();
        password_pin = new JPasswordField();
        btn_login = new JButton("LOGIN");
        btn_register = new JButton("CREATE ACCOUNT");
        
        side_left = new JPanel();
        side_right = new JPanel();
        
        //Font
        Font font = new Font("Sans Serif", Font.PLAIN, 25);
        
        //Setting the contents of the left panel
        side_left.setLayout(null);
        lbl_title.setFont(font);
        lbl_title.setForeground(Color.white);
        lbl_title.setBounds(90,50,350,30);
        side_left.add(lbl_title);
        
        lbl_description.setBounds(40,100,300,30);
        lbl_description.setForeground(Color.white);
        lbl_description.setFont(new Font("Sans Serif", Font.PLAIN,14));
        side_left.add(lbl_description);
        
        lbl_luring.setBounds(25,250,300,30);
        lbl_luring.setForeground(Color.white);
        lbl_luring.setFont(new Font("Sans Serif", Font.PLAIN,20));
        side_left.add(lbl_luring);
        
        btn_register.setBounds(70,300,180,40);
        btn_register.setForeground(Color.white);
        btn_register.setFont(new Font("Sans Serif",Font.BOLD,12));
        btn_register.setBackground(new Color(17, 79, 89));
        btn_register.setFocusPainted(false);
        side_left.add(btn_register);
        
        //Setting the contents of the right panel
        side_right.setLayout(null);       
        ImageIcon icon_account = new ImageIcon("images/account.png");        
        lbl_icon.setIcon(icon_account);
        lbl_icon.setBounds(143,100,64,64);
        side_right.add(lbl_icon);
        
        Font font1 = new Font(Font.SANS_SERIF,Font.PLAIN,15);
        
        lbl_accountnumber.setFont(font1);
        lbl_accountnumber.setForeground(new Color(17, 79, 89));
        lbl_accountnumber.setBounds(110,170,150,30);
        side_right.add(lbl_accountnumber);
        
        textField_accountnumber.setFont(font1);
        textField_accountnumber.setBounds(70,200,200,30);
        side_right.add(textField_accountnumber);
        
        lbl_pin.setFont(font1);
        lbl_pin.setForeground(new Color(17, 79, 89));
        lbl_pin.setBounds(155,230,100,30);
        side_right.add(lbl_pin);
        
        
        password_pin.setBounds(120,260,100,30);
	password_pin.setDocument(new MaxLengthTextDocument(5));        
        side_right.add(password_pin);
        
        btn_login.setForeground(Color.white);
        btn_login.setFont(new Font("Sans Serif",Font.BOLD,12));
        btn_login.setBackground(new Color(17, 79, 89));
        btn_login.setFocusPainted(false);
        btn_login.setBounds(70,300,200,40);
        side_right.add(btn_login);
        
        
        //Setting up the side panels        
        side_right.setBackground(new Color(255, 255, 255));
        side_left.setBackground(new Color(17, 79, 89));
        side_left.setBounds(0, 0, 350, 500);
        side_right.setBounds(350, 0, 350, 500);
        add(side_left);
        add(side_right);
        
        
        
    }
    
//    Setting a method to handle events like create account and login
//    
    private void setHandling(){
        //Event handling
        btn_register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               AccountCreation frame =  new AccountCreation();
               frame.setVisible(true);
               Dashboard.this.dispose();
            }
        });
        
        btn_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textField_accountnumber.getText().isEmpty() || String.valueOf(password_pin.getPassword()).isEmpty()) {
                    JOptionPane.showMessageDialog(Dashboard.this, "Please fill in all fields");
                }else{
                    
                    EntityOperations.Login(textField_accountnumber.getText(), String.valueOf(password_pin.getPassword()), Dashboard.this);
                    
                }
            }
        });
        
    }
    
    public static void main(String[] args) {
        Dashboard dashboard = new Dashboard();
        dashboard.setVisible(true);
    }
}
