/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitsim;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Swapnesh
 */
public class BITSimEWalletBalance extends JFrame{
    private JPanel panelMessage;
    private JLabel labelMessage;
    private JPanel panelUsername;
    private JLabel labelUsername;
    private JTextField textUsername;
    private JPanel panelPIN;
    private JLabel labelPIN;
    private JPasswordField textPIN;
    private JPanel panelBalance;
    private JLabel labelBalance;
    private JTextField textBalance;
    private JPanel panelButtons;
    private JButton buttonCheck;
    private JButton buttonBack;
    
    public BITSimEWalletBalance(String title, final BITSimUser user) {
        super(title);
        setLayout(new FlowLayout());
        setVisible(true);
        setSize(400, 230);
        addWindowListener(
            new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            }
        );
        
        BITSim.positionFrame(this);
        
        panelMessage = new JPanel();
        
        labelMessage = new JLabel("Status : Enter PIN and press Check Balance");
        panelMessage.add(labelMessage);
        
        add(panelMessage);
        
        panelUsername = new JPanel();
        
        labelUsername = new JLabel("Username : ");
        panelUsername.add(labelUsername);
        
        textUsername = new JTextField(20);
        textUsername.setEditable(false);
        textUsername.setText(user.getName());
        panelUsername.add(textUsername);
        
        add(panelUsername);
        
        panelPIN = new JPanel();
        
        labelPIN = new JLabel("PIN : ");
        panelPIN.add(labelPIN);
        
        textPIN = new JPasswordField(20);
        panelPIN.add(textPIN);
        
        add(panelPIN);
        
        panelBalance = new JPanel();
        
        labelBalance = new JLabel("Balance : ");
        panelBalance.add(labelBalance);
        
        textBalance = new JTextField(20);
        textBalance.setEditable(false);
        panelBalance.add(textBalance);
        
        add(panelBalance);
        
        panelButtons = new JPanel();
        
        buttonCheck = new JButton("Check Balance");
        buttonCheck.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String PIN = String.valueOf(textPIN.getPassword());
                if(PIN.equals("")) 
                    labelMessage.setText("Status : PIN cannot be empty!");
                else {
                    if(PIN.equals(String.valueOf(user.getPIN()))) {
                        labelMessage.setText("Status : Balance check successful!");
                        textPIN.setText("");
                        textBalance.setText("Rs. " + user.getEWalletBalance());
                        buttonCheck.setEnabled(false);
                    }
                    else {
                        labelMessage.setText("Status : Incorrect PIN!");
                        textPIN.setText("");
                    }
                }
            }
        });
        panelButtons.add(buttonCheck);
        
        buttonBack = new JButton("Back");
        buttonBack.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new BITSimPlay("Play", BITSim.users.get(BITSim.getUserIndex(user)));
                dispose();
            }
        });
        panelButtons.add(buttonBack);
        
        add(panelButtons);
        
    }
}
