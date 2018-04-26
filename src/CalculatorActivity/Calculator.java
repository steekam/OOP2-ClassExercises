
package CalculatorActivity;

/**
 *
 * @author steekam
 */


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;

public class Calculator extends JFrame {
    
    //Declaring all the variables
    private JButton[] numbers;
    private JButton plus;
    private JButton minus;
    private JButton divide;
    private JButton multiply;
    private JButton clear;
    private JButton dot;
    private JButton equals;
    private JTextField operations;
    private JTextField operations2;
    private JTextField output;
    private JPanel top;
    private JPanel input;
    private JPanel results;
    private JPanel keypad;
    
    //Initializing the variables in the constructor
    
    public Calculator(){
        super("Simple Math");
        
        numbers = new JButton[10];
        for(int i = 0; i < 10; i++){
            numbers[i] = new JButton("" + i);
            numbers[i].addActionListener(new ButtonListener());
        } 

        plus = new JButton("+");
        plus.addActionListener(new ButtonListener());
        minus = new JButton("-");
        minus.addActionListener(new ButtonListener());
        divide = new JButton("/");
        divide.addActionListener(new ButtonListener());
        multiply = new JButton ("*");
        multiply.addActionListener(new ButtonListener());
        clear = new JButton("C");
        clear.addActionListener(new ButtonListener());
        dot = new JButton (".");
        dot.addActionListener(new ButtonListener());
        equals = new JButton("=");
        equals.addActionListener(new ButtonListener());
        operations = new JTextField(9);
        operations.addFocusListener(new OnFocusListener());
        operations.grabFocus();
        operations2 = new JTextField(9);
        operations2.addFocusListener(new OnFocusListener());
        output = new JTextField(15);
        
        output.setEditable(false);
        
        //Setting up the top panel
        top = new JPanel(new GridLayout(2,1));                
        
        input = new JPanel();
        input.add(operations);
        input.add(operations2);
        top.add(input);
        
        results = new JPanel(); 
        results.add(output);
        top.add(results);
        
        add(top, BorderLayout.NORTH);
        
        //Laying out the keypad panel
        keypad = new JPanel();
        keypad.setLayout(new GridLayout(4,1));     
        keypad.add(getRow(clear,numbers[7], numbers[8], numbers[9], plus));
        keypad.add(getRow(numbers[4], numbers[5], numbers[6], minus));
        keypad.add(getRow(numbers[1], numbers[2], numbers[3], multiply));
        keypad.add(getRow(dot, numbers[0], equals, divide));
        add(keypad);
        
        //Changing size of textfields
        Font font1 = new Font("SansSerif", Font.PLAIN, 22);
        output.setFont(font1);                 
        

    }

    

    private JPanel getRow(JButton b1, JButton b2, JButton b3, JButton b4){
            JPanel row = new JPanel();
            row.setLayout(new FlowLayout(FlowLayout.CENTER));
            row.add(b1);
            row.add(b2);
            row.add(b3);
            row.add(b4);
            return row;
    }
    
    private JPanel getRow(JButton b1, JButton b2, JButton b3, JButton b4, JButton b5){
            JPanel row = new JPanel();
            row.setLayout(new FlowLayout(FlowLayout.CENTER));
            row.add(b1);
            row.add(b2);
            row.add(b3);
            row.add(b4);
            row.add(b5);
            return row;
    }
    
    
    JTextField focusedBox;
    JButton clickedButton;
    class ButtonListener implements ActionListener {
        
       

        @Override
        public void actionPerformed(ActionEvent e) {
              String results = null;
            if (e.getSource() == plus) {
                //adding the two operands
                changeFocus(plus);

                
            } else if(e.getSource() == minus) {
                changeFocus(minus);
                
            } else if(e.getSource() == multiply){
                changeFocus(multiply);
                
            } else if(e.getSource() == divide){
                changeFocus(divide);
                
            } else if(e.getSource() == dot) {
                 String current = focusedBox.getText();
                 focusedBox.setText( current + ".");
            } else if(e.getSource() == clear) {
                operations.setText("");
                operations2.setText("");
                output.setText("");
                operations.grabFocus();
            } else if(e.getSource() == equals){
                
                if (clickedButton == plus) {
                    double input1 = Double.parseDouble(operations.getText());
                    double input2 = Double.parseDouble(operations2.getText());
                    results = (Double.toString(input1 + input2));                    
                } else if(clickedButton == minus){
                    double input1 = Double.parseDouble(operations.getText());
                    double input2 = Double.parseDouble(operations2.getText());
                    results = (Double.toString(input1 - input2));
                    
                } else if(clickedButton == multiply){
                     double input1 = Double.parseDouble(operations.getText());
                    double input2 = Double.parseDouble(operations2.getText());
                    results = (Double.toString(input1 * input2));
                    
                    
                } else if(clickedButton == divide){
                    double input1 = Double.parseDouble(operations.getText());
                    double input2 = Double.parseDouble(operations2.getText());
                    results = (Double.toString(input1 / input2));
                    
                }                
                
                output.setText(results);
            }
            for (int i = 0; i < 10; i++) {
                if (e.getSource() == numbers[i]) {
                    String current = focusedBox.getText();
                    focusedBox.setText( current + i);
                }
            }
        }
        
    }
    
    class OnFocusListener implements FocusListener{

        @Override
        public void focusGained(FocusEvent e) {
            focusedBox = (JTextField) e.getSource();
        }

        @Override
        public void focusLost(FocusEvent e) {
            
        }
        
    }
    
    public void changeFocus (JButton b1){
        operations2.grabFocus();
        clickedButton = b1;
    }
    
    
    public static void main(String[] args) {
        Calculator simpleMath = new Calculator();
        
                
        simpleMath.setSize(350, 400);
        simpleMath.setDefaultCloseOperation(EXIT_ON_CLOSE);
        simpleMath.setVisible(true);
        simpleMath.setResizable(false);
       
        
    }
    
}
