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
import java.util.Iterator;
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
public class BITSimLogin extends JFrame{
    private BITSimFrame frame;
    
    private JPanel panelMessage;
    private JLabel labelMessage;
    private JPanel panelUsername;
    private JLabel labelUsername;
    private JTextField textUsername;
    private JPanel panelPassword;
    private JLabel labelPassword;
    private JPasswordField textPassword;
    private JPanel panelButton;
    private JButton buttonCreate;
    private JButton buttonCancel;
    
    /**
     *
     * @param title
     * @param frame
     */
    public BITSimLogin(String title){
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
        
        BITSimFrame.isLogin = true;
        
        panelMessage = new JPanel();
        
        labelMessage = new JLabel("Status : Enter credentials");
        panelMessage.add(labelMessage);
        
        add(panelMessage);
        
        panelUsername = new JPanel();
        
        labelUsername = new JLabel("Username : ");
        panelUsername.add(labelUsername);
        
        textUsername = new JTextField(20);
        panelUsername.add(textUsername);
        
        add(panelUsername);
        
        panelPassword = new JPanel();
        
        labelPassword = new JLabel("Password : ");
        panelPassword.add(labelPassword);
        
        textPassword = new JPasswordField(20);
        panelPassword.add(textPassword);
        
        add(panelPassword);
        
        panelButton = new JPanel();
        
        buttonCreate = new JButton("Login");
        buttonCreate.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(textUsername.getText().equals("")) {
                    labelMessage.setText("Status : Username cannot be blank!");
                    textPassword.setText("");
                }
                else if(String.valueOf(textPassword.getPassword()).equals("")) {
                    labelMessage.setText("Status : Password cannot be blank!");
                    textPassword.setText("");
                }
                else{
                    boolean isPresent = false;
                    BITSimUser user = null;
                    Iterator<BITSimUser> it = BITSim.users.iterator();
                    while(it.hasNext()){
                        user = it.next();
                        if(user.getName().equals(textUsername.getText())){
                            isPresent = true;
                            break;
                        }
                    }
                    if(!isPresent){
                        labelMessage.setText("Status : No such username!");
                        textPassword.setText("");
                    }
                    else {
                        if(user.getPassword().equals(String.valueOf(textPassword.getPassword()))){
                            BITSimFrame.isLogin = false;
                            if(!user.getEWalletState())
                                new BITSimEWallet("E-Wallet Setup", user);
                            else
                                new BITSimPlay("Play", user);
                            dispose();
                        }
                        else{
                            labelMessage.setText("Status : Invalid password!");
                            textPassword.setText("");
                        }
                    }
                }
            }
        });
        panelButton.add(buttonCreate);
        
        buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                BITSimFrame.isLogin = false;
                new BITSimFrame("BITSim");
                dispose();
            }
        });
        panelButton.add(buttonCancel);
        
        add(panelButton);
    }
}
