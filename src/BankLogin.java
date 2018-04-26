import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BankLogin extends JFrame {
    
    private JPanel pnl_deposit, pnl_withdraw, pnl_status;
    private JLabel lbl_accountNumber,lbl_deposit,lbl_withdraw,lbl_status,lbl_instructions,lbl_compliment;
    private JTextField txtfield_accountNumber;
    private JButton btn_register;
    
    public BankLogin(){
        
        setSize(500, 500);
        setTitle("THE STUDENT BANK");
        setLocation(450, 150);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        lbl_deposit = new JLabel("DEPOSIT");
        lbl_withdraw = new JLabel("WITHDRAW");
        lbl_status = new JLabel("STATUS");
        lbl_instructions = new JLabel("Click one option to transact.");
        lbl_compliment = new JLabel("Thank you for banking with us");
        
        pnl_deposit = new JPanel();
        setPanelColor(pnl_deposit);
        setBoundsLabel(lbl_deposit, pnl_deposit);
        pnl_deposit.add(lbl_deposit);
        pnl_deposit.addMouseListener(new MouseClicked(this));
        
        pnl_withdraw = new JPanel();
        pnl_withdraw.setLayout(null);
        setPanelColor(pnl_withdraw);
        lbl_withdraw.setBounds(20,40,90,30);
        pnl_withdraw.add(lbl_withdraw);
        pnl_withdraw.addMouseListener(new MouseClicked(this));
        
        pnl_status = new JPanel();
        setPanelColor(pnl_status);
        setBoundsLabel(lbl_status, pnl_status);
        pnl_status.add(lbl_status);
        pnl_status.addMouseListener(new MouseAdapter() {
            //
            //
            //
            @Override            
            public void mouseClicked(MouseEvent e) {
              if (!txtfield_accountNumber.getText().isEmpty()) {
                    
                    if (Banking.store.containsKey(txtfield_accountNumber.getText())) {
                        Banking temp_user = new Banking();
                        temp_user = Banking.store.get(txtfield_accountNumber.getText());
                        
                        JOptionPane.showMessageDialog(BankLogin.this, "Account # => "+temp_user.getAccountNumber()+"\n Balance => "+temp_user.getBalance(),"STATUS",JOptionPane.PLAIN_MESSAGE);                        
                        
                      }else {
                          JOptionPane.showMessageDialog(BankLogin.this, "Couldn't find account","ACCOUNT NOT FOUND",JOptionPane.ERROR_MESSAGE);
                      }
                    
                }  
            }
                        
                
        
        
        });

        txtfield_accountNumber = new JTextField(10);
        
        lbl_accountNumber = new JLabel("Enter your account number:");
        
        btn_register = new JButton("REGISTER");
        btn_register.addActionListener(new ButtonLister(this));
        
        btn_register.setBounds(5,5,100,30);
        lbl_accountNumber.setBounds(150, 50, 200, 30);
        txtfield_accountNumber.setBounds(180,90,150,30);
        lbl_instructions.setBounds(150, 130, 220, 30);
        pnl_deposit.setBounds(40, 180, 120, 120);
        pnl_withdraw.setBounds(200,180,120,120);
        pnl_status.setBounds(350,180,120,120);        
        lbl_compliment.setBounds(160,400,300,30);
        
        add(lbl_accountNumber);
        add(txtfield_accountNumber);
        add(pnl_deposit);
        add(pnl_withdraw);
        add(pnl_status);
        add(lbl_instructions);
        add(lbl_compliment);
        add(btn_register);
        
        txtfield_accountNumber.grabFocus();
       
       
    }
   
    
    
    
    public final void setPanelColor(JPanel panel){
        panel.setBackground(new Color(180, 185, 193));
    }
    
    public final void setBoundsLabel(JLabel label, JPanel panel){
        panel.setLayout(null);
        label.setBounds(30,40,90,30);
    }

    private static class ButtonLister implements ActionListener {
        JFrame frame;
        public ButtonLister(JFrame frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new BankRegister().setVisible(true);
            frame.dispose();
            
        }
    }
    
     class MouseClicked implements MouseListener{
         
         JFrame frame;
        public MouseClicked(JFrame frame){
            this.frame = frame;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == pnl_deposit) {                           
                //
                //
                //-----------------------------------------------------To make an account deposit-------------------------------------------------
                //
                if (!txtfield_accountNumber.getText().isEmpty()) {
                    
                    if (Banking.store.containsKey(txtfield_accountNumber.getText())) {
                        Banking temp_user = new Banking();
                        temp_user = Banking.store.get(txtfield_accountNumber.getText());
                        
                        try{
                            String value = JOptionPane.showInputDialog(frame, "Enter amount to deposit:", "DEPOSIT",JOptionPane.PLAIN_MESSAGE);
                            if (!value.isEmpty()) {
                                double amountDeposit = Double.parseDouble(value);                        
                                temp_user.deposit(amountDeposit);

                                JOptionPane.showMessageDialog(frame, "Deposit successful. \n New balance => "+temp_user.getBalance(),"SUCCESS",JOptionPane.PLAIN_MESSAGE);
                            }
                        }catch(HeadlessException | NumberFormatException ex){
                            
                        }
                      
                      }else {
                          JOptionPane.showMessageDialog(frame, "Couldn't find account","ACCOUNT NOT FOUND",JOptionPane.ERROR_MESSAGE);
                      }
                    
                }
                
            }else if(e.getSource() == pnl_withdraw){
                //
                //
                //-----------------------------------------------------To make an account withdrawal-------------------------------------------------
                //
                if (!txtfield_accountNumber.getText().isEmpty()) {
                    
                    if (Banking.store.containsKey(txtfield_accountNumber.getText())) {
                        Banking temp_user = new Banking();
                        temp_user = Banking.store.get(txtfield_accountNumber.getText());
                        
                        try {
                            String value = JOptionPane.showInputDialog(frame, "Enter amount to withdraw:", "WITHDRAWAL",JOptionPane.PLAIN_MESSAGE);
                            if (!value.isEmpty()) {
                                double amount = Double.parseDouble(value);                        
                                int success = temp_user.withdraw(amount);
                                
                                if (success == 0) {
                                    JOptionPane.showMessageDialog(frame, "You have insufficient funds. \n Current Balance => "+temp_user.getBalance(),"INSUFFICIENT FUNDS",JOptionPane.ERROR_MESSAGE);
                                }else{
                                    JOptionPane.showMessageDialog(frame, "Withdrawal successful. \n New balance => "+temp_user.getBalance(),"SUCCESS",JOptionPane.PLAIN_MESSAGE);
                                }                                
                            }
                            
                        } catch (HeadlessException | NumberFormatException | NullPointerException exe ) {
                            
                        }
                        
                      }else {
                          JOptionPane.showMessageDialog(frame, "Couldn't find account","ACCOUNT NOT FOUND",JOptionPane.ERROR_MESSAGE);
                      }
                    
                }
            }else if (e.getSource() == pnl_status) {
                //
                //
                //-----------------------------------------------------To check account status -------------------------------------------------
                //
                if (!txtfield_accountNumber.getText().isEmpty()) {
                    
                    if (Banking.store.containsKey(txtfield_accountNumber.getText())) {
                        Banking temp_user = new Banking();
                        temp_user = Banking.store.get(txtfield_accountNumber.getText());
                        
                        JOptionPane.showMessageDialog(frame, "Account # => "+temp_user.getAccountNumber()+"\n Balance => "+temp_user.getBalance(),"STATUS",JOptionPane.PLAIN_MESSAGE);                      
                        
                        
                      }else {
                          JOptionPane.showMessageDialog(frame, "Couldn't find account","ACCOUNT NOT FOUND",JOptionPane.ERROR_MESSAGE);
                      }
                    
                }
                
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
       
        
    }
     
     public static void main(String[] args) {
        new BankLogin().setVisible(true);
    }
    
}
