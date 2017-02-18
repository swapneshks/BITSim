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
import java.util.ListIterator;
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
public class BITSimAccount extends JFrame{
    private boolean isCreating;
    private JPanel panelMessage;
    private JLabel labelMessage;
    private JPanel panelUsername;
    private JLabel labelUsername;
    private JTextField textUsername;
    private JPanel panelPassword1;
    private JPanel panelPassword2;
    private JLabel labelPassword1;
    private JLabel labelPassword2;
    private JPasswordField textPassword1;
    private JPasswordField textPassword2;
    private JPanel panelButton;
    private JButton buttonCreate;
    private JButton buttonCancel;
    
    /**
     *
     * @param title
     */
    public BITSimAccount(String title){
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
        
        isCreating = true;
        BITSimFrame.isAccountCreating = true;
        
        panelMessage = new JPanel();
        
        labelMessage = new JLabel("Status : Enter account details");
        panelMessage.add(labelMessage);
        
        add(panelMessage);
        
        panelUsername = new JPanel();
        
        labelUsername = new JLabel("Username : ");
        panelUsername.add(labelUsername);
        
        textUsername = new JTextField(20);
        panelUsername.add(textUsername);
        
        add(panelUsername);
        
        panelPassword1 = new JPanel();
        
        labelPassword1 = new JLabel("Password : ");
        panelPassword1.add(labelPassword1);
        
        textPassword1 = new JPasswordField(20);
        panelPassword1.add(textPassword1);
        
        add(panelPassword1);
        
        panelPassword2 = new JPanel();
        
        labelPassword2 = new JLabel("Enter password again : ");
        panelPassword2.add(labelPassword2);
        
        textPassword2 = new JPasswordField(20);
        panelPassword2.add(textPassword2);
        
        panelPassword2.add(textPassword2);
        
        add(panelPassword2);
        
        panelButton = new JPanel();
        
        buttonCreate = new JButton("Create");
        buttonCreate.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(isCreating){
                    if(textUsername.getText().equals(""))
                        labelMessage.setText("Status : Username cannot be blank!");
                    else if(String.valueOf(textPassword1.getPassword()).equals(""))
                        labelMessage.setText("Status : Password cannot be blank!");
                    else if(String.valueOf(textPassword2.getPassword()).equals(""))
                        labelMessage.setText("Status : Enter password again!");
                    else {
                        boolean isPresent = false;
                        Iterator<BITSimUser> it = BITSim.users.iterator();
                        while(it.hasNext()){
                            if(it.next().getName().equals(textUsername.getText())){
                                isPresent = true;
                                break;
                            }
                        }
                        if(isPresent)
                            labelMessage.setText("Status : Username already exists!");
                        else{
                            if(String.valueOf(textPassword1.getPassword()).equals(String.valueOf(textPassword2.getPassword()))) {
                                BITSim.users.add(new BITSimUser(textUsername.getText(), String.valueOf(textPassword1.getPassword())));
                                BITSim.writeUserDetails();
                                labelMessage.setText("Status : Account created! Want to create another? (Yes/No)");
                                textUsername.setText("");
                                textUsername.setEditable(false);
                                textPassword1.setText("");
                                textPassword1.setEditable(false);
                                textPassword2.setText("");
                                textPassword2.setEditable(false);
                                buttonCreate.setText("Yes");
                                buttonCancel.setText("No");        
                                isCreating = false;
                            }
                            else {
                                textPassword1.setText("");
                                textPassword2.setText("");
                                labelMessage.setText("Status : Passwords don't match!");
                            }
                        }
                    }
                }
                else {
                    isCreating = true;
                    labelMessage.setText("Status : Enter account details");
                    buttonCreate.setText("Create");
                    buttonCancel.setText("Cancel");
                    textUsername.setEditable(true);
                    textPassword1.setEditable(true);
                    textPassword2.setEditable(true);
                }
            }
        });
        panelButton.add(buttonCreate);
        
        buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                BITSimFrame.isAccountCreating = false;
                new BITSimFrame("BITSim");
                dispose();
            }
        });
        panelButton.add(buttonCancel);
        
        add(panelButton);
    }
}
