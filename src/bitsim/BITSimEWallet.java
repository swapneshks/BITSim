/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitsim;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Swapnesh
 */
public class BITSimEWallet extends JFrame{
    private boolean isGenerating;
    private int userIndex;
    
    private JPanel panelMessage;
    private JLabel labelMessage1;
    private JLabel labelMessage2;
    private JPanel panelPassword;
    private JLabel labelPassword;
    private JPasswordField textPassword;
    private JPanel panelPIN;
    private JLabel labelPIN;
    private JTextField textPIN;
    private JPanel panelButton;
    private JButton buttonObtainPIN;
    private JButton buttonCancel;
    
    /**
     *
     * @param title
     * @param user
     */
    public BITSimEWallet(String title, final BITSimUser user){
        super(title);
        setLayout(new FlowLayout());
        setVisible(true);
        setSize(400, 200);
        addWindowListener(
            new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            }
        );
        
        BITSim.positionFrame(this);
        
        isGenerating = true;
        
        panelMessage = new JPanel();
        panelMessage.setLayout(new GridLayout(2, 1));
        
        labelMessage1 = new JLabel("Logged in as '" + user.getName() +"'\n", SwingConstants.CENTER);
        panelMessage.add(labelMessage1);
        labelMessage2 = new JLabel("Status : Enter account password to obtain E-Wallet PIN.");
        panelMessage.add(labelMessage2);
        
        add(panelMessage);
        
        panelPassword = new JPanel();
        
        labelPassword = new JLabel("Password : ");
        panelPassword.add(labelPassword);
        
        textPassword = new JPasswordField(20);
        panelPassword.add(textPassword);
        
        add(panelPassword);
        
        panelPIN = new JPanel();
        
        labelPIN = new JLabel("PIN : ");
        panelPIN.add(labelPIN);
        
        textPIN = new JTextField(20);
        textPIN.setEditable(false);
        panelPIN.add(textPIN);
        
        add(panelPIN);
        
        panelButton = new JPanel();
        
        buttonObtainPIN = new JButton("Generate PIN");
        buttonObtainPIN.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(isGenerating) {
                    if(String.valueOf(textPassword.getPassword()).equals(""))
                        labelMessage2.setText("Status : Password cannot be blank!");
                    else {
                        if(!String.valueOf(textPassword.getPassword()).equals(user.getPassword())) {
                            labelMessage2.setText("Status : Invalid Password!");
                            textPassword.setText("");
                        }
                        else {
                            isGenerating = false;
                            Random random = new Random();
                            int PIN = (int) (random.nextFloat()*10000);
                            if(PIN <= 0)
                                PIN = 1;
                            else if(PIN >= 10000)
                                PIN = 9999;
                            char[] pin = new char[4];
                            for(int i=0; i<4; i++) {
                                if(PIN == 0)
                                    pin[i] = 48;
                                else 
                                    pin[i] = (char) (PIN%10 + 48);
                                PIN /= 10;
                            }
                            
                            char swap;
                            swap = pin[0];
                            pin[0] = pin[3];
                            pin[3] = swap;
                            
                            swap = pin[1];
                            pin[1] = pin[2];
                            pin[2] = swap;
                            
                            textPIN.setText(new String(pin));
                            userIndex = BITSim.getUserIndex(user);
                            BITSim.users.get(userIndex).setPIN(pin);
                            BITSim.users.get(userIndex).setEWalletState(true);
                            BITSim.users.get(userIndex).setEWalletBalance(5000);
                            BITSim.writeUserDetails();
                            labelMessage2.setText("Status : PIN Generated. Do not share with anyone!");
                            buttonObtainPIN.setText("Proceed");
                            buttonCancel.setEnabled(false);
                        }
                    }
                }
                else {
                    new BITSimPlay("Play", BITSim.users.get(userIndex));
                    dispose();
                }
            }
        });
        panelButton.add(buttonObtainPIN);
        
        buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new BITSimFrame("BITSim");
                dispose();
            }
        });
        panelButton.add(buttonCancel);
        
        add(panelButton);
    }
}
