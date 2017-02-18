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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Swapnesh
 */
public class BITSimPay extends JFrame{
    private JPanel panelMessage;
    private JLabel labelMessage;
    private JPanel panelUsername;
    private JLabel labelUsername;
    private JTextField textUsername;
    private JPanel panelAmount;
    private JLabel labelAmount;
    private JTextField textAmount;
    private JPanel panelPIN;
    private JLabel labelPIN;
    private JPasswordField textPIN;
    private JPanel panelButtons;
    private JButton buttonPay;
    private JButton buttonCancel;
    
    public BITSimPay(String title, final BITSimShopFrame shopFrame, final ArrayList<BITSimProduct> productList, final BITSimShopFrame frame, final BITSimUser user, final int shopIndex, final float amount) {
        super(title);
        setVisible(true);
        setSize(400, 250);
        setLayout(new FlowLayout());
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
        
        labelMessage = new JLabel("Status : Enter PIN and press Pay to make the payment.");
        panelMessage.add(labelMessage);
        
        add(panelMessage);
        
        panelUsername = new JPanel();
        
        labelUsername = new JLabel("Username : ");
        panelUsername.add(labelUsername);
        
        textUsername = new JTextField(user.getName(), 20);
        textUsername.setEditable(false);
        panelUsername.add(textUsername);
        
        add(panelUsername);
        
        panelAmount = new JPanel();
        
        labelAmount = new JLabel("Amount to be paid : ");
        panelAmount.add(labelAmount);
        
        textAmount = new JTextField("Rs. " + String.valueOf(amount), 20);
        textAmount.setEditable(false);
        panelAmount.add(textAmount);
        
        add(panelAmount);
        
        panelPIN = new JPanel();
        
        labelPIN = new JLabel("PIN : ");
        panelPIN.add(labelPIN);
        
        textPIN = new JPasswordField(20);
        panelPIN.add(textPIN);
        
        add(panelPIN);
        
        panelButtons = new JPanel();
        
        buttonPay = new JButton("Pay");
        buttonPay.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(buttonPay.getText().equals("Pay")) {
                    if(String.valueOf(textPIN.getPassword()).equals(""))
                        labelMessage.setText("Status : PIN cannot be empty!");
                    else {
                        if(String.valueOf(textPIN.getPassword()).equals(String.valueOf(user.getPIN()))) {
                            if(user.getEWalletBalance() < amount) {
                                textPIN.setEditable(false);
                                buttonPay.setEnabled(false);
                                buttonCancel.setText("Back");
                                labelMessage.setText("Status : Transaction failed, insufficient balance.");
                            }
                            else {
                                textPIN.setEditable(false);
                                textPIN.setText("");
                                textAmount.setText("");
                                buttonPay.setText("Save e-Receipt");
                                buttonCancel.setText("Exit Shop");
                                labelMessage.setText("Status : Payment successful.");
                                BITSim.setBoughtQuantity(user, productList);
                                BITSim.users.get(BITSim.users.indexOf(user)).setEWalletBalance(user.getEWalletBalance() - amount);
                                BITSim.writeUserDetails();
                                BITSim.writeProducts(shopIndex);
                            }
                        } 
                        else {
                            labelMessage.setText("Status : Incorrect PIN!");
                            textPIN.setText("");
                        }
                    }
                }
                else if(buttonPay.getText().equals("Save e-Receipt")) {
                    try {
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setSelectedFile(new File("receipt"));
                        BITSimTxtFilter filter = new BITSimTxtFilter();
                        fileChooser.setFileFilter(filter);
                        if(fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                            File f = fileChooser.getSelectedFile();
                            String ext = (filter.accept(f)?"":".txt");
                            File file = new File(f + ext);
                            if(file.exists()) {
                                int result = JOptionPane.showConfirmDialog(BITSimPay.this, "The file already exists! Overwrite it?", "Existing File", JOptionPane.YES_NO_OPTION);
                                if(result == JOptionPane.YES_OPTION) {
                                    FileWriter fileWriter = new FileWriter(file.getCanonicalPath());
                                    PrintWriter printWriter = new PrintWriter(new BufferedWriter(fileWriter));
                                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                    Date date = new Date();
                                    printWriter.println("e-Receipt generated on " + dateFormat.format(date));
                                    printWriter.println("===========================================");
                                    printWriter.println("");
                                    Iterator<BITSimProduct> it = productList.iterator();
                                    BITSimProduct printProduct = null;
                                    while(it.hasNext()) {
                                        printProduct = it.next();
                                        printWriter.println(printProduct.getProductName() + " : " + printProduct.getQuantityAvailable() + " @ Rs. " + printProduct.getPrice());
                                    }
                                    printWriter.close();
                                    labelMessage.setText("Status : e-Receipt generated and saved to disk.");
                                }
                                else if(result == JOptionPane.NO_OPTION) {
                                    labelMessage.setText("Status : e-Receipt could not be saved to disk!");
                                }
                            }
                            else {
                                FileWriter fileWriter = new FileWriter(file.getCanonicalPath());
                                PrintWriter printWriter = new PrintWriter(new BufferedWriter(fileWriter));
                                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                Date date = new Date();
                                printWriter.println("e-Receipt generated on " + dateFormat.format(date) + " @ " + BITSimPlay.getShopName(shopIndex));
                                printWriter.println("======================================================");
                                printWriter.println("");
                                Iterator<BITSimProduct> it = productList.iterator();
                                BITSimProduct printProduct = null;
                                while(it.hasNext()) {
                                    printProduct = it.next();
                                    printWriter.println(printProduct.getProductName() + " : " + printProduct.getQuantityAvailable() + " @ Rs. " + printProduct.getPrice());
                                }
                                printWriter.close();
                                labelMessage.setText("Status : e-Receipt generated and saved to disk.");
                            }
                        }
                    } catch(IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        });
        panelButtons.add(buttonPay);
        
        buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(buttonCancel.getText().equals("Cancel") || buttonCancel.getText().equals("Back"))
                    dispose();
                else if(buttonCancel.getText().equals("Exit Shop")) {
                    new BITSimPlay("Play", BITSim.users.get(BITSim.getUserIndex(user)));
                    frame.dispose();
                    dispose();
                }
                shopFrame.changeButtonsState(true);
            }
        });
        panelButtons.add(buttonCancel);
        
        add(panelButtons);
    }
}
